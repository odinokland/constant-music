plugins {
	`kotlin-dsl`
	kotlin("plugin.serialization") version embeddedKotlinVersion
}

gradlePlugin {
	plugins {
		register("modPlatform") {
			id = "mod-platform"
			implementationClass = "ModPlatformPlugin"
		}
		register("modRoot") {
			id = "mod-root"
			implementationClass = "ModRootPlugin"
		}
	}
}

repositories {
	mavenCentral()
	gradlePluginPortal()
	maven("https://maven.fabricmc.net/") { name = "Fabric" }
	maven("https://maven.neoforged.net/releases/") { name = "NeoForged" }
	maven("https://maven.minecraftforge.net/") { name = "Forge" }
	maven("https://jitpack.io") { name = "Jitpack" }
	exclusiveContent {
		forRepository { maven("https://maven.quiltmc.org/repository/release") }
		filter { includeGroup("org.quiltmc.parsers") }
	}
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

dependencies {
	implementation(libs.kikugie.postprocess)
	implementation(libs.kikugie.stonecutter)
	implementation(libs.kikugie.loomx)
	implementation(libs.mod.publish.plugin)
	implementation(libs.foojay.resolver)
	implementation(libs.fletching.table)
	implementation(libs.vanniktech.maven.publish)
	implementation(libs.serialization.json)
	implementation(libs.serialization.toml)
	libs.mixinextras.common.let {
		compileOnly(it)
		annotationProcessor(it)
	}
}
