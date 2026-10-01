import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

val isCi = System.getenv("CI") == "true"
gradle.startParameter.isParallelProjectExecutionEnabled = !isCi
gradle.startParameter.isBuildCacheEnabled = !isCi
gradle.startParameter.isConfigureOnDemand = !isCi

pluginManagement {
	repositories {
		mavenLocal()
		mavenCentral()
		gradlePluginPortal()
		maven("https://maven.fabricmc.net/") { name = "Fabric" }
		maven("https://maven.neoforged.net/releases/") { name = "NeoForged" }
		maven("https://maven.minecraftforge.net/") { name = "Forge" }
		maven("https://maven.kikugie.dev/snapshots") { name = "KikuGie Snapshots" }
		maven("https://maven.kikugie.dev/releases") { name = "KikuGie Releases" }
		maven("https://maven.parchmentmc.org") { name = "ParchmentMC" }
	}
	includeBuild("build-logic")

	plugins {
		id("org.gradle.toolchains.foojay-resolver-convention") version providers.gradleProperty("foojay.version").get()
		id("dev.kikugie.stonecutter") version providers.gradleProperty("stonecutter.version").get()
		id("dev.kikugie.loom-back-compat") version providers.gradleProperty("loomx.version").get()
	}
}

buildscript {
	repositories {
		mavenCentral() // Jackson is hosted here
	}
	dependencies {
		// Pull in the regular Maven dependency for the settings script classpath
		classpath("com.fasterxml.jackson.module:jackson-module-kotlin:2.22.3")
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

		for ((version, loaders) in rootData.versions) {
			match(version, *loaders.toTypedArray())
		}
		vcsVersion = "1.19.2-forge"
	}
}

