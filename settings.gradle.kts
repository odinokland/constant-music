val isCi = System.getenv("CI") == "true"
gradle.startParameter.isParallelProjectExecutionEnabled = !isCi
gradle.startParameter.isBuildCacheEnabled = !isCi
gradle.startParameter.isConfigureOnDemand = !isCi

pluginManagement {
	repositories {
		gradlePluginPortal()
		mavenCentral()
		maven("https://maven.fabricmc.net/")
		maven("https://maven.neoforged.net/releases/")
		maven("https://maven.minecraftforge.net")
		maven("https://maven.kikugie.dev/releases")
		maven("https://maven.kikugie.dev/snapshots") { name = "KikuGie Snapshots" }
		exclusiveContent {
			forRepository {
				maven("https://repo.spongepowered.org/repository/maven-public") { name = "Sponge" }
			}
			filter { includeGroupAndSubgroups("org.spongepowered") }
		}
	}
}

plugins {
	id("dev.kikugie.stonecutter") version "0.7.5"
	id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

val commonVersions =
	providers.gradleProperty("stonecutter_enabled_common_versions").orNull?.split(",")?.map { it.trim() } ?: emptyList()
val fabricVersions =
	providers.gradleProperty("stonecutter_enabled_fabric_versions").orNull?.split(",")?.map { it.trim() } ?: emptyList()
val forgeVersions =
	providers.gradleProperty("stonecutter_enabled_forge_versions").orNull?.split(",")?.map { it.trim() } ?: emptyList()
val neoforgeVersions =
	providers.gradleProperty("stonecutter_enabled_neoforge_versions").orNull?.split(",")?.map { it.trim() }
		?: emptyList()
val dists = mapOf(
	"common" to commonVersions,
	"fabric" to fabricVersions,
	"forge" to forgeVersions,
	"neoforge" to neoforgeVersions
)
val uniqueVersions = dists.values.flatten().distinct()

stonecutter {
	kotlinController = true
	centralScript = "build.gradle.kts"

	create(rootProject) {
		versions(*uniqueVersions.toTypedArray())
		vcsVersion = "1.21.8"

		dists.forEach { (branchName, branchVersions) ->
			branch(branchName) {
				versions(*branchVersions.toTypedArray())
			}
		}
	}
}

rootProject.name = "constant-music"
