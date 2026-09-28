@file:Suppress("unused")

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonObject
import me.modmuss50.mpp.platforms.modrinth.ModrinthEnvironment
import org.gradle.api.NamedDomainObjectContainer
import java.util.*

sealed class Loader(val id: String) {
	abstract val modManifestPath: String
	abstract val excludedResources: List<String>

	open val isFabricLike: Boolean = false

	open fun manifestPathFor(ctx: Context): String = modManifestPath
	open fun excludedResourcesFor(ctx: Context): List<String> = excludedResources

	abstract fun generateManifest(ctx: Context): String
	abstract fun generateTestManifest(ctx: Context): String

	object Fabric : Loader("fabric") {
		override val isFabricLike = true
		override val modManifestPath = "fabric.mod.json"
		override val excludedResources = listOf(
			"META-INF/mods.toml", "META-INF/neoforge.mods.toml", "aw/*.cfg", ".cache", "pack.mcmeta", "data/constantmusic/test_instance/*.json"
		)

		override fun generateManifest(ctx: Context): String {
			val widener = ctx.resolvedAccessFile()
			val widenerPath = "aw/$widener"
			val manifest = FabricManifest(
				id = ctx.modId,
				name = ctx.modName,
				version = ctx.baseVersion,
				authors = ctx.authors,
				contributors = ctx.contributors,
				contact = mapOf(
					"sources" to ctx.sourcesUrl, "issues" to ctx.issuesUrl, "homepage" to ctx.homepageUrl
				),
				custom = ctx.discordUrl.takeIf { it.isNotEmpty() }?.let { url ->
					buildJsonObject {
						putJsonObject("modmenu") {
							putJsonObject("links") {
								put("modmenu.discord", url)
							}
						}
					}
				},
				description = ctx.description,
				icon = "assets/icon.png",
				license = ctx.licenseName,
				environment = when (ctx.effectiveEnvironment) {
					ModrinthEnvironment.CLIENT_ONLY, ModrinthEnvironment.SINGLEPLAYER_ONLY -> "client"

					ModrinthEnvironment.DEDICATED_SERVER_ONLY -> "server"

					ModrinthEnvironment.SERVER_ONLY, ModrinthEnvironment.SERVER_ONLY_CLIENT_OPTIONAL,
					ModrinthEnvironment.CLIENT_ONLY_SERVER_OPTIONAL, ModrinthEnvironment.CLIENT_AND_SERVER,
					ModrinthEnvironment.CLIENT_OR_SERVER_PREFERS_BOTH, ModrinthEnvironment.CLIENT_OR_SERVER -> "*"
				},
				accessWidener = widenerPath,
				entrypoints = mapOf(
					"main" to listOf("${ctx.modGroup}.${ctx.modId}.platform.fabric.FabricEntrypoint"),
					"preLaunch" to listOf("com.llamalad7.mixinextras.MixinExtrasBootstrap::init"),
					"modmenu" to listOf("${ctx.modGroup}.${ctx.modId}.platform.fabric.FabricModMenuIntegration"),
				),
				mixins = listOf("${ctx.modId}.mixins.json"),
				depends = ctx.extension.dependencies.required.associate { it.modid.get() to it.fabricLikeVersionRange.get() },
				recommends = ctx.extension.dependencies.optional.associate { it.modid.get() to it.fabricLikeVersionRange.get() },
				breaks = ctx.extension.dependencies.incompatible.associate { it.modid.get() to it.fabricLikeVersionRange.get() },
				provides = ctx.extension.dependencies.embeds.map { it.modid.get() }
			)
			return JSON.encodeToString(manifest)
		}

		override fun generateTestManifest(ctx: Context): String {
			val manifest = FabricManifest(
				id = "${ctx.modId}_gametest",
				name = "Constant Music Game Tests",
				version = "1.0.0",
				authors = ctx.authors,
				contributors = ctx.contributors,
				contact = mapOf(
					"sources" to ctx.sourcesUrl,
					"issues" to ctx.issuesUrl,
					"homepage" to ctx.homepageUrl
				),
				custom = ctx.discordUrl.takeIf { it.isNotEmpty() }?.let { url ->
					buildJsonObject {
						putJsonObject("modmenu") {
							putJsonObject("links") {
								put("modmenu.discord", url)
							}
						}
					}
				},
				description = ctx.description,
				license = ctx.licenseName,
				environment = "*",
				entrypoints = mapOf(
					"fabric-gametest" to listOf("${ctx.modGroup}.${ctx.modId}.gametest.platform.GametestTestRunner")
				),
				mixins = listOf(),
				depends = mapOf(
					"fabricloader" to "*",
					"minecraft" to "*",
					"fabric-api" to "*",
					ctx.modId to "*"
				),
			)
			return JSON.encodeToString(manifest)
		}
	}

	sealed class ForgeLike(id: String) : Loader(id) {
		override val excludedResources = listOf(
			"fabric.mod.json", "aw/*.accesswidener", ".cache"
		)

		override fun generateManifest(ctx: Context): String {
			val forgeDeps = mutableListOf<ForgeDependency>()

			fun addDeps(container: NamedDomainObjectContainer<Dependency>, type: String) {
				container.forEach {
					forgeDeps.add(
						ForgeDependency(
							modId = it.modid.get(),
							side = it.environment.get().uppercase(Locale.getDefault()),
							versionRange = it.forgeLikeVersionRange.get(),
							mandatory = type == "required",
							type = type
						)
					)
				}
			}

			addDeps(ctx.extension.dependencies.required, "required")
			addDeps(ctx.extension.dependencies.optional, "optional")
			addDeps(ctx.extension.dependencies.incompatible, "incompatible")
			val logoFile = if (id == "neoforge" && ctx.stonecutter.eval(ctx.currentMcVersion, ">=26.2")) null else "icon.png"
			val bannerFile = if (id == "neoforge" && ctx.stonecutter.eval(ctx.currentMcVersion, ">=26.2")) "assets/banner.png" else null

			val manifest = ForgeManifest(
				license = ctx.licenseName,
				issueTrackerURL = ctx.issuesUrl,
				clientSideOnly = !ctx.environmentPhysicalServer,
				mods = listOf(
					ForgeMod(
						modId = ctx.modId,
						displayName = ctx.modName,
						version = ctx.baseVersion,
						displayURL = ctx.homepageUrl,
						modUrl = ctx.homepageUrl,
						// Forge resolves logoFile with getRootResource(), which only accepts
						// a filename. The icon is copied to the archive root below.
						logoFile = logoFile,
						authors = ctx.authors.joinToString(", "),
						credits = "${ctx.authors.joinToString(", ")} Contributors: ${ctx.contributors.joinToString(", ")}",
						description = ctx.description,
						iconFile = "icon.png",
						bannerFile = bannerFile,
					)
				),
				dependencies = mapOf(ctx.modId to forgeDeps),
				mixins = listOf(ForgeMixin("${ctx.modId}.mixins.json"))
			)

			return TOML.encodeToString(manifest)
		}

		override fun generateTestManifest(ctx: Context): String {
			val forgeDeps = mutableListOf<ForgeDependency>()

			fun addDeps(container: NamedDomainObjectContainer<Dependency>, type: String) {
				container.forEach {
					forgeDeps.add(
						ForgeDependency(
							modId = it.modid.get(),
							side = it.environment.get().uppercase(Locale.getDefault()),
							versionRange = it.forgeLikeVersionRange.get(),
							mandatory = type == "required",
							type = type
						)
					)
				}
			}

			addDeps(ctx.extension.dependencies.required, "required")
			addDeps(ctx.extension.dependencies.optional, "optional")
			addDeps(ctx.extension.dependencies.incompatible, "incompatible")
			forgeDeps.add(
				ForgeDependency(
					modId = ctx.modId,
					side = "BOTH",
					versionRange = "[1, )",
					mandatory = true,
					type = "required",
					ordering = "BEFORE"
				)
			)

			val manifest = ForgeManifest(
				license = ctx.licenseName,
				issueTrackerURL = ctx.issuesUrl,
				mods = listOf(
					ForgeMod(
						modId = "${ctx.modId}_gametest",
						displayName = "Constant Music Game Tests",
						version = "1.0.0",
						displayURL = ctx.homepageUrl,
						modUrl = ctx.homepageUrl,
						authors = ctx.authors.joinToString(", "),
						credits = "${ctx.authors.joinToString(", ")} Contributors: ${ctx.contributors.joinToString(", ")}",
						description = ctx.description,
					)
				),
				dependencies = mapOf(ctx.modId + "_gametest" to forgeDeps)
			)

			return TOML.encodeToString(manifest)
		}
	}

	object NeoForge : ForgeLike("neoforge") {
		override val modManifestPath = "META-INF/neoforge.mods.toml"
		override val excludedResources = (super.excludedResources + "META-INF/mods.toml") + "pack.mcmeta" + "data/constantmusic/test_instance/*.json"

		private fun isLegacy(ctx: Context) = !ctx.stonecutter.eval(ctx.currentMcVersion, ">=1.20.5")

		override fun manifestPathFor(ctx: Context) =
			if (isLegacy(ctx)) "META-INF/mods.toml" else "META-INF/neoforge.mods.toml"

		override fun excludedResourcesFor(ctx: Context) =
			if (isLegacy(ctx)) (super.excludedResources + "META-INF/neoforge.mods.toml") + "pack.mcmeta"
			else excludedResources
	}

	object Forge : ForgeLike("forge") {
		override val modManifestPath = "META-INF/mods.toml"
		override val excludedResources = super.excludedResources + "META-INF/neoforge.mods.toml"
		val mixinConfigAttribute = "MixinConfigs"
	}

	companion object {
		fun of(id: String): Loader = when (id) {
			"fabric" -> Fabric
			"neoforge" -> NeoForge
			"forge" -> Forge
			else -> error("Unknown loader: '$id'")
		}
	}
}
