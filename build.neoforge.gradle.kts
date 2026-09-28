plugins {
	id("mod-platform")
	id("net.neoforged.moddev")
}

stonecutter {
	val (version, loader) = current.project.split('-', limit = 2)
	properties.tags(version, loader)

	swaps["gametest_annotation"] = when {
		(eval(current.version, ">=1.21.5")) -> "@GameTest(structure = TEMPLATE_NAME, environment = ENVIRONMENT_NAME)"
		else -> "@GameTest(templateNamespace = Constants.MOD_ID, template = TEMPLATE_NAME)"
	}

	replacements.string(current.parsed >= "1.21.11") {
		replace("ResourceLocation", "Identifier")
		replace("location()", "identifier()")
	}

	replacements.string(current.parsed >= "26.1") {
		replace("Holder<TestEnvironmentDefinition>", "Holder<TestEnvironmentDefinition<?>>")
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
		val devJvmArgs = propsList("mod", "dev_jvm_args") +
			propsList("mod", "dev_jvm_args_mixin_debug")
		mods.create(prop("mod.id")) { sourceSet(java.sourceSets["main"]) }
		configureEach {
			disableIdeRun()
			systemProperty("terminal.ansi", "true")
			jvmArguments.addAll(devJvmArgs)
		}
		register("client") {
			client()
			gameDirectory = file("run/client")
			programArgument("--username=Dev")
			systemProperty("forge.logging.console.level", "debug")

			sourceSet.set(java.sourceSets["main"])
			loadedMods.set(listOf(mods[prop("mod.id")]))
		}
		register("server") {
			server()
			gameDirectory = file("run/server")
			sourceSet.set(java.sourceSets["main"])
			loadedMods.set(listOf(mods[prop("mod.id")]))
		}
		mods.create("${prop("mod.id")}_gametest") { sourceSet(java.sourceSets["gametest"]) }
		register("gameTestServer") {
			type = "gameTestServer"
			gameDirectory = file("run/server")
			systemProperty("neoforge.enableGameTest", "true")
			systemProperty("neoforge.enabledGameTestNamespaces", "${prop("mod.id")},${prop("mod.id")}_gametest")
//			systemProperty("forge.gametest.report-file", file("gametest-report.xml").absolutePath)
			sourceSet.set(java.sourceSets["gametest"])
			loadedMods.set(listOf(mods[prop("mod.id")], mods["${prop("mod.id")}_gametest"]))
		}
	}
	sourceSets["main"].resources.srcDir("${rootDir}/versions/datagen/${sc.current.version.split("-")[0]}/src/main/generated")
}

repositories {
	mavenCentral()
	strictMaven("https://api.modrinth.com/maven", "maven.modrinth") { name = "Modrinth" }
}

dependencies {
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
