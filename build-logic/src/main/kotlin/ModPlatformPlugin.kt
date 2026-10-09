@file:Suppress("unused", "DuplicatedCode")

import dev.kikugie.fletching_table.extension.FletchingTableExtension
import org.gradle.api.DefaultTask
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.testing.Test
import org.gradle.jvm.tasks.Jar
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.jvm.toolchain.JavaToolchainService
import org.gradle.kotlin.dsl.*
import org.gradle.language.jvm.tasks.ProcessResources
import org.gradle.plugins.ide.idea.model.IdeaModel
import javax.inject.Inject
import kotlin.text.split

abstract class GenerateModManifestTask : DefaultTask() {
	@get:Input
	abstract val content: Property<String>

	@get:OutputFile
	abstract val outputFile: RegularFileProperty

	@TaskAction
	fun generate() {
		val file = outputFile.get().asFile
		file.parentFile.mkdirs()
		file.writeText(content.get())
	}
}

abstract class ModPlatformPlugin @Inject constructor() : Plugin<Project> {
	override fun apply(project: Project) = with(project) {
		val inferredLoader = Loader.of(project.buildFile.name.substringAfter('.').replace(".gradle.kts", ""))

		val extension = extensions.create("platform", ModPlatformExtension::class.java).apply {
			loader.convention(inferredLoader.id)
		}

		when (inferredLoader) {
			is Loader.Fabric -> {
				extension.jarTask.convention(providers.provider {
					extensions.getByType<dev.kikugie.loomx.LoomCompatProjectExtension>().modJar.name
				})
				extension.sourcesJarTask.convention(providers.provider {
					extensions.getByType<dev.kikugie.loomx.LoomCompatProjectExtension>().modSourcesJar.name
				})
			}
			is Loader.Forge -> {
				if (project.sc.eval(project.sc.current.version, "<1.20.6")) {
					extension.jarTask.convention("renameJarJar")
				} else {
					extension.jarTask.convention("jar")
				}
				extension.sourcesJarTask.convention("sourcesJar")
			}
			else -> {
				extension.jarTask.convention("jar")
				extension.sourcesJarTask.convention("sourcesJar")
			}
		}

		listOf(
			"org.jetbrains.kotlin.jvm",
			"com.google.devtools.ksp",
			"dev.kikugie.fletching-table",
			"me.modmuss50.mod-publish-plugin"
		).forEach {
			apply(
				plugin = it
			)
		}

		val (version, loader) = name.split('-', limit = 2)
		project.sc.properties.tags(version, loader)

		val ctx = Context(
			project = this,
			extension = extension,
			loader = Loader.of(extension.loader.get()),
			stonecutter = project.sc
		)
		configureProject(ctx)
	}

	private fun Project.configureProject(ctx: Context) {
		listOf("java", "idea").forEach { apply(plugin = it) }

		version = ctx.fullVersion
		ctx.extension.requiredJava.set(ctx.javaVersion)
		println("Setting java version to ${ctx.javaVersion.majorVersion} (required by ${ctx.loader.id})")

		if (ctx.loader.isFabricLike) {
			ctx.extension.dependencies {
				required("java") { fabricLikeVersionRange = ">=${ctx.javaVersion.majorVersion}" }
			}
		}

		configureFletchingTable(ctx)
		configureTesting(ctx)
		registerGenerateManifestTask(ctx)
		registerGenerateTestManifestTask(ctx)
		configureJarTask(ctx)
		configureIdea()
		configureProcessResources(ctx)
		configureJava(ctx)
		registerBuildAndCollectTask(ctx)

		configureModPublishing(ctx)

		if (envTrue("PUB_MAVEN_ENABLE")) {
			configureMavenPublishing(ctx)
		}
	}

	private fun Project.configureJava(ctx: Context) {
		extensions.configure<JavaPluginExtension>("java") {
			withSourcesJar()
			withJavadocJar()
			toolchain.languageVersion = JavaLanguageVersion.of(ctx.javaVersion.majorVersion)
			sourceCompatibility = ctx.javaVersion
			targetCompatibility = ctx.javaVersion
		}
		val javaToolchains = extensions.getByType<JavaToolchainService>()
		if (ctx.loader is Loader.ForgeLike) {
			tasks.withType<JavaExec>().matching { it.name.startsWith("run") }.configureEach {
				javaLauncher.set(javaToolchains.launcherFor {
					languageVersion.set(JavaLanguageVersion.of(ctx.javaVersion.majorVersion))
				})
			}
		}
	}

	private fun Project.configureTesting(ctx: Context) {
		val gametestInclude = configurations.maybeCreate("gametestInclude").apply {
			isCanBeConsumed = false
			isCanBeResolved = true
		}

		fun configureSourceSets(sourceSetName: String) {
			val java = the<JavaPluginExtension>()

			val sourceSet =
				java.sourceSets.findByName(sourceSetName)
					?: java.sourceSets.create(sourceSetName)

			sourceSet.apply {
				compileClasspath += java.sourceSets.getByName("main").compileClasspath
				runtimeClasspath += java.sourceSets.getByName("main").runtimeClasspath

				java.registerFeature(sourceSetName) {
					usingSourceSet(this@apply)
				}

				dependencies {
					implementationConfigurationName(
						java.sourceSets["main"].output
					)
				}
			}

			dependencies {
				"${sourceSetName}CompileOnly"(
					java.sourceSets.getByName("main").output
				)
			}
		}

		val libs = extensions
			.getByType<VersionCatalogsExtension>()
			.named("libs")

		configureSourceSets("test")
		configureSourceSets("gametest")

		tasks.withType<Test>().configureEach {
			useJUnitPlatform()
			testLogging {
				events("passed", "skipped", "failed")
			}
		}

		dependencies {
			"testImplementation"(libs.findLibrary("junit-jupiter").get())
			"testImplementation"(libs.findLibrary("assertj-core").get())
			"testRuntimeOnly"(libs.findLibrary("junit-platform-launcher").get())
		}
	}

	private fun Project.registerGenerateManifestTask(ctx: Context) {
		val manifestOutputDir = layout.buildDirectory.dir("generated/modManifest")
		val generateTask = tasks.register<GenerateModManifestTask>("generateModManifest") {
			content.set(ctx.loader.generateManifest(ctx))
			outputFile.set(layout.buildDirectory.file("generated/modManifest/${ctx.loader.manifestPathFor(ctx)}"))
		}

		the<JavaPluginExtension>().sourceSets.named("main") { resources.srcDir(manifestOutputDir) }
		tasks.named<ProcessResources>("processResources") { dependsOn(generateTask) }
	}

	private fun Project.registerGenerateTestManifestTask(ctx: Context) {
		val manifestOutputDir = layout.buildDirectory.dir("generated/modTestManifest")
		val gametestInclude = configurations.named("gametestInclude")
		val generateTask = tasks.register<GenerateModManifestTask>("generateModTestManifest") {
			inputs.files(gametestInclude)

			content.set(
				provider {
					val jars = gametestInclude.get()
						.resolve()
						.map { "META-INF/jars/${it.name}" }

					ctx.loader.generateTestManifest(ctx, jars)
				}
			)

			outputFile.set(layout.buildDirectory.file("generated/modTestManifest/${ctx.loader.manifestPathFor(ctx)}"))
		}

		the<JavaPluginExtension>().sourceSets.named("gametest") { resources.srcDir(manifestOutputDir) }
		tasks.named<ProcessResources>("processGametestResources") { dependsOn(generateTask) }
	}

	private fun Project.configureProcessResources(ctx: Context) {
		tasks.named<ProcessResources>("processResources") {
			dependsOn(tasks.named("stonecutterGenerate"), "kspKotlin")
			inputs.property("modId", ctx.modId)
			inputs.property("javaVersion", ctx.javaVersion.majorVersion)
			val isForge = ctx.loader is Loader.Forge
			filesMatching("*.mixins.json*") {
				// Forge 1.20.6 runs on Java 21, but its bundled Mixin version
				// only recognizes compatibility levels through JAVA_17. The
				// compatibility level controls Mixin bytecode behavior, not the
				// Java toolchain used to compile the mod.
				val mixinJava = if (isForge && ctx.javaVersion > JavaVersion.VERSION_17) {
					"JAVA_17"
				} else {
					"JAVA_${ctx.javaVersion.majorVersion}"
				};
				expand(mapOf(
					"java" to mixinJava,
					"modId" to ctx.modId
				))

			}
			exclude(ctx.loader.excludedResourcesFor(ctx))
			if (ctx.loader is Loader.ForgeLike) {
				val accessFileName = ctx.resolvedAccessFile()
				into("META-INF") {
					from("src/main/resources/aw") {
						include(accessFileName)
					}
					rename { "accesstransformer.cfg" }
				}
				exclude("aw/**")
				// Forge's ModListScreen reads logoFile as a root resource, so move
				// the shared icon to the archive root (Fabric keeps the assets path).
				eachFile {
					if (path == "assets/icon.png") {
						path = "icon.png"
					}
				}
			}
		}
		tasks.named<ProcessResources>("processGametestResources") {
			dependsOn(tasks.named("stonecutterGenerateGametest"), "kspGametestKotlin")
			inputs.property("modId", ctx.modGametestId)
			inputs.property("javaVersion", ctx.javaVersion.majorVersion)
			val isForge = ctx.loader is Loader.Forge
			exclude(ctx.loader.excludedResourcesFor(ctx))
			filesMatching("*.mixins.json*") {
				// Forge 1.20.6 runs on Java 21, but its bundled Mixin version
				// only recognizes compatibility levels through JAVA_17. The
				// compatibility level controls Mixin bytecode behavior, not the
				// Java toolchain used to compile the mod.
				val mixinJava = if (isForge && ctx.javaVersion > JavaVersion.VERSION_17) {
					"JAVA_17"
				} else {
					"JAVA_${ctx.javaVersion.majorVersion}"
				};
				expand(mapOf(
					"java" to mixinJava,
					"modId" to ctx.modGametestId
				))

			}
			if (ctx.loader is Loader.ForgeLike) {
				filesMatching("META-INF/mods.toml") {
					expand(
						mapOf(
							"loader" to ctx.loader.id
						)
					)
				}
			}
		}
	}

	private fun Project.configureJarTask(ctx: Context) {
		val generateTask = tasks.named("generateModManifest")
		val generateTestTask = tasks.named("generateModTestManifest")
		tasks.withType<Jar>().configureEach {
			archiveBaseName.set(ctx.modId)
			if (name == "gametestJar") {
				dependsOn(generateTestTask)
			} else {
				dependsOn(generateTask)
				if (ctx.loader is Loader.Forge) {
					manifest.attributes(ctx.loader.mixinConfigAttribute to "${ctx.modId}.mixins.json")
				}
			}
		}
	}

	private fun Project.configureIdea() {
		extensions.configure<IdeaModel>("idea") {
			module {
				isDownloadJavadoc = true
				isDownloadSources = true
			}
		}
	}

	private fun Project.configureFletchingTable(ctx: Context) {
		extensions.configure<FletchingTableExtension> {
			j52j.register("main") {
				extension("json", "**/*.json5")
			}
			mixins.create("main").apply {
				mixin("default", "${ctx.modId}.mixins.json")
			}
		}
	}

	private fun Project.registerBuildAndCollectTask(ctx: Context) {
		tasks.register<Copy>("buildAndCollect") {
			dependsOn("build")
			from(
				tasks.named(ctx.extension.jarTask.get()),
				tasks.named(ctx.extension.sourcesJarTask.get()),
				tasks.named("javadocJar"),
				tasks.named("gametestJar")
			)
			into(rootProject.layout.buildDirectory.file("libs/${ctx.basicVersion}"))
			group = "build"
		}

		tasks.register("buildForTests") {
			dependsOn("build")
			dependsOn("gametestJar")
			group = "build"
		}

	}
}
