import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

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
}

buildscript {
	repositories {
		mavenCentral() // Jackson is hosted here
	}
	dependencies {
		// Pull in the regular Maven dependency for the settings script classpath
		classpath("com.fasterxml.jackson.module:jackson-module-kotlin:2.18.2")
	}
}

plugins {
	id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
	id("dev.kikugie.stonecutter") version "0.10-alpha.7"
	id("dev.kikugie.loom-back-compat") version "0.4.2"
}



val mapper = jacksonObjectMapper()
val versionsFile = file("versions.json")
val typeRef = object : TypeReference<Map<String, Map<String, List<String>>>>() {}
val rootData: Map<String, Map<String, List<String>>>? = mapper.readValue(versionsFile, typeRef)
val versionsMap: Map<String, List<String>> = rootData?.get("versions") ?: emptyMap()

stonecutter {
	create(rootProject) {

		fun match(version: String, vararg loaders: String) =
			loaders.forEach { version("$version-$it", version).buildscript = "build.$it.gradle.kts" }

		versionsMap.forEach { (version, loaders) -> match(version, *loaders.toTypedArray()) }
//		match("26.1.2", "fabric", "neoforge")
//		match("1.21.7", "fabric", "neoforge")
//		match("1.21.1", "fabric", "neoforge")
//		match("1.19.2", "fabric", "forge")

		vcsVersion = "1.19.2-forge"
	}
}

