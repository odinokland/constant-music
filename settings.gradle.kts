import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

val isCi = System.getenv("CI") == "true"
gradle.startParameter.isParallelProjectExecutionEnabled = if (isCi) {
	false
} else {
	providers.gradleProperty("org.gradle.parallel").orNull?.toBoolean() ?: false
}
gradle.startParameter.isBuildCacheEnabled = !isCi
gradle.startParameter.isConfigureOnDemand = !isCi

val isSingle = System.getenv("SINGLE_PROJECT") == "true"
val projectVersion: String? = System.getenv("PROJECT_VERSION")
val projectModLoader: String? = System.getenv("PROJECT_MOD_LOADER")


pluginManagement {
	repositories {
		mavenLocal()
		mavenCentral()
		gradlePluginPortal()
		maven("https://maven.fabricmc.net/") { name = "Fabric" }
		maven("https://maven.neoforged.net/releases/") { name = "NeoForged" }
		maven("https://maven.minecraftforge.net/") { name = "Forge" }
		maven("https://maven.parchmentmc.org") { name = "ParchmentMC" }
		maven("https://maven.quiltmc.org/repository/release") { name = "QuiltMC" }
		maven {
			name = "KikuGie Snapshots"
			url = uri("https://maven.kikugie.dev/snapshots/")

			content {
				includeGroupAndSubgroups("dev.kikugie")
			}
		}
		maven {
			name = "KikuGie Releases"
			url = uri("https://maven.kikugie.dev/releases/")

			content {
				includeGroupAndSubgroups("dev.kikugie")
			}
		}
	}
	includeBuild("build-logic")

	val foojayVersion = providers.gradleProperty("foojay.version").get()
	val stonecutterVersion = providers.gradleProperty("stonecutter.version").get()
	val loomxVersion = providers.gradleProperty("loomx.version").get()

	plugins {
		id("org.gradle.toolchains.foojay-resolver-convention") version foojayVersion
		id("dev.kikugie.stonecutter") version stonecutterVersion
		id("dev.kikugie.loom-back-compat") version loomxVersion
	}
}

buildscript {
	repositories {
		mavenCentral() // Jackson is hosted here
	}
	dependencies {
		// Pull in the regular Maven dependency for the settings script classpath
		classpath("com.fasterxml.jackson.module:jackson-module-kotlin:2.22.4")
	}
}

plugins {
	id("org.gradle.toolchains.foojay-resolver-convention")
	id("dev.kikugie.stonecutter")
	id("dev.kikugie.loom-back-compat")
}

data class VersionData(val versions: List<GameVersion>)
data class GameVersion(val version: String, val loaders: List<String>, val java: Int)

val mapper = jacksonObjectMapper()
val versionsFile = file("versions.json")
val typeRef = object : TypeReference<VersionData>() {}
val rootData: VersionData = mapper.readValue(versionsFile, typeRef)

stonecutter {
	create(rootProject) {

		fun match(version: String, vararg loaders: String) =
			loaders.forEach { version("$version-$it", version).buildscript = "build.$it.gradle.kts" }

		if (isSingle && !projectVersion.isNullOrEmpty() && !projectModLoader.isNullOrEmpty()) {
			match(projectVersion, projectModLoader)
		} else {
			for ((version, loaders) in rootData.versions) {
				match(version, *loaders.toTypedArray())
			}
			vcsVersion = "1.19.2-forge"
		}
	}
}

gradle.lifecycle.beforeProject {
	repositories.matching { it.name == "KikuGie Releases" || it.name == "KikuGie Snapshots" }.configureEach {
		if (this is MavenArtifactRepository) {
			content {
				includeGroupAndSubgroups("dev.kikugie")
			}
		}
	}
}
