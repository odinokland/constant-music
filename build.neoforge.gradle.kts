import org.gradle.plugins.ide.idea.model.IdeaModel
import org.jetbrains.gradle.ext.runConfigurations
import org.jetbrains.gradle.ext.settings

plugins {
	id("mod-platform")
	id("net.neoforged.moddev")
}

stonecutter {
	val (version, loader) = current.project.split('-', limit = 2)
	properties.tags(version, loader)

	replacements.string(current.parsed >= "1.21.11") {
		replace("ResourceLocation", "Identifier")
		replace("location()", "identifier()")
	}
}

platform {
	loader = "neoforge"
	dependencies {
		required("minecraft") {
			forgeLikeVersionRange = "[${prop("deps.minMinecraft")},)"
		}
		required("neoforge") {
			forgeLikeVersionRange.set("[1,)")
		}
	}
}

sourceSets {
	maybeCreate("gametest")
}

neoForge {
	version = prop("deps.neoforge")
	accessTransformers.from(getAccessFile(AccessType.TRANSFORMER))
	validateAccessTransformers = true

	if (hasProperty("deps.parchment")) parchment {
		val (mc, ver) = prop("deps.parchment").split(':')
		mappingsVersion = ver
		minecraftVersion = mc
	}

	runs {
		mods.create(prop("mod.id")) { sourceSet(java.sourceSets["main"]) }
		configureEach {
			disableIdeRun()
			systemProperty("terminal.ansi", "true")
		}
		register("client") {
			client()
			gameDirectory = file("run/client")
			ideName = "NeoForge Client (${stonecutter.current.version})"
			programArgument("--username=Dev")
			systemProperty("forge.logging.console.level", "debug")

			sourceSet.set(java.sourceSets["main"])
			loadedMods.set(listOf(mods[prop("mod.id")]))
		}
		register("server") {
			server()
			gameDirectory = file("run/server")
			ideName = "NeoForge Server (${stonecutter.current.version})"

			sourceSet.set(java.sourceSets["main"])
			loadedMods.set(listOf(mods[prop("mod.id")]))
		}
		mods.create("${prop("mod.id")}_gametest") { sourceSet(java.sourceSets["gametest"]) }
		register("gameTestServer") {
			type = "gameTestServer"
			gameDirectory = file("run/server")
			ideName = "NeoForge GameTest Server (${stonecutter.current.version})"
			systemProperty("neoforge.enableGameTest", "true")
			systemProperty("neoforge.enabledGameTestNamespaces", "${prop("mod.id")},${prop("mod.id")}_gametest")
//			systemProperty("forge.gametest.report-file", file("gametest-report.xml").absolutePath)
//			sourceSet.set(sourceSets[""])

			sourceSet.set(java.sourceSets["gametest"])
			loadedMods.set(listOf(mods[prop("mod.id")], mods["${prop("mod.id")}_gametest"]))
		}
	}
	sourceSets["main"].resources.srcDir("${rootDir}/versions/datagen/${sc.current.version.split("-")[0]}/src/main/generated")
	//sourceSets["main"].resources.srcDir("${layout.projectDirectory}/build/generated")
}

repositories {
	mavenCentral()
	strictMaven("https://api.modrinth.com/maven", "maven.modrinth") { name = "Modrinth" }
}

dependencies {
	// implementation(libs.moulberry.mixinconstraints)
	// jarJar(libs.moulberry.mixinconstraints)
	implementation(jarJar(libs.mixinextras.neoforge.get()) as Any)
}

tasks.named("createMinecraftArtifacts") {
	dependsOn(tasks.named("stonecutterGenerate"))
}

afterEvaluate {
	tasks.named<JavaExec>("runGameTestServer") {
		classpath(sourceSets["gametest"].output)
		//dependsOn(":${ project.name }:runData")
	}
}
