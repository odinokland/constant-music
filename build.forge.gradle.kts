plugins {
	id("mod-platform")
	id("net.minecraftforge.gradle")
	id("net.minecraftforge.renamer")
	id("net.minecraftforge.jarjar")
}

stonecutter {
	val (version, loader) = current.project.split('-', limit = 2)
	properties.tags(version, loader)

	swaps["gametest_annotation"] = when {
		(eval(current.version, ">=1.21.5")) -> "@GameTest(structure = TEMPLATE_NAME, environment = ENVIRONMENT_NAME)"
		(eval(current.version, ">1.20.1")) -> "@GameTest(template = TEMPLATE_NAME)"
		else -> "@GameTest(templateNamespace = Constants.MOD_ID, template = TEMPLATE_NAME)"
	}

	replacements.string(current.parsed >= "1.21.11") {
		replace("ResourceLocation", "Identifier")
		replace("location()", "identifier()")
	}
}

platform {
	loader = "forge"
	dependencies {
		required("minecraft") {
			forgeLikeVersionRange = "[${prop("deps.minMinecraft")},)"
		}
		required("forge") {
			forgeLikeVersionRange.set("[1,)")
		}
	}
}

if (stonecutter.eval(stonecutter.current.version, "<1.20.5 ")) {
	renamer.enableMixinRefmaps {
		config("${prop("mod.id")}.mixins.json")
		source(sourceSets.main.get(), prop("mod.id"))
		jar(project.tasks.named(JavaPlugin.JAR_TASK_NAME, Jar::class.java))
	}

	afterEvaluate {
		minecraft.runs.forEach { r ->
			renamer.mixin.run(r)
		}
	}

	renamer.classes(tasks.named<Jar>("jar")) {
		output.set(tasks.named<Jar>("jar").get().archiveFile)
		dependsOn("jarJar")
		mappings(renamer.mixin.generatedMappings)
	}

	// Fixes the maven publishing using a broken jar
	tasks.named<Jar>("sourcesJar") {
		dependsOn("renameJar")
	}
} else {
	tasks.withType<Jar>().configureEach {
		if (name == "sourcesJar") dependsOn("jarJar")
	}
}

sourceSets.configureEach {
	val dir = layout.buildDirectory.dir("sourceSets/$name")
	output.setResourcesDir(dir.get())
	java.destinationDirectory.set(dir)
}

jarJar.register() {
	archiveClassifier = null
}

tasks.named<Jar>("jar") {
	archiveClassifier = "slim"
}

val generateTests = stonecutter.eval(stonecutter.current.version, ">= 1.21.5")

minecraft {
	mappings("official", prop("deps.minecraft"))
	val devJvmArgs = propsList("mod", "dev_jvm_args") +
		propsList("mod", "dev_jvm_args_mixin_debug")
	runs {
		configureEach {
			if (stonecutter.eval(stonecutter.current.version, "<1.20.5 ")) {
				environment("MOD_CLASSES", "{source_roots}")
			}
			workingDir.convention(layout.projectDirectory.dir("run"))

			systemProperty("eventbus.api.strictRuntimeChecks", true)
			systemProperty("forge.enabledGameTestNamespaces", "${prop("mod.id")},${prop("mod.id")}_gametest")
			args("--mixin.config", "${prop("mod.id")}.mixins.json")
			mods {
				create("constantmusic") {
					source(sourceSets["main"])
				}
				create("constantmusic_gametest") {
					source(sourceSets["gametest"])
				}
			}
			jvmArgs.addAll(devJvmArgs)
//			if (stonecutter.eval(stonecutter.current.version, ">=1.17") && stonecutter.eval(stonecutter.current.version, "<=1.18")) {
//				jvmArgs("--add-opens=java.base/java.lang.invoke=ALL-UNNAMED")
//			}
		}
		register("client") {
			workingDir.convention(layout.projectDirectory.dir("run/client"))
		}
		register("gameTestServer") {
			workingDir.convention(layout.projectDirectory.dir("run/server"))
		}
		if (generateTests) {
			// Gradle run will not be registered, since it is only a requirement for the GameTestServer run.
			// Forge doesn't apply the json data needed via Code, so we have to generate it before running the Gametest Server.
			register("data") {
				workingDir.convention(layout.projectDirectory.dir("run/data"))
				args(
					"--mod",
					"${prop("mod.id")}_gametest",
					"--all",
					"--output",
					file("src/gametest/generated").absolutePath
				)
			}
		}
	}
}

repositories {
	minecraft.mavenizer(this)
	maven(fg.forgeMaven)
	maven(fg.minecraftLibsMaven)
	mavenCentral()
}

dependencies {
	implementation(minecraft.dependency("net.minecraftforge:forge:${prop("deps.minecraft")}-${prop("deps.forge")}"))
	if (stonecutter.eval(stonecutter.current.version, "<1.20.5")) {
		annotationProcessor("org.spongepowered:mixin:${libs.versions.mixin.get()}:processor")
	}
	compileOnly(annotationProcessor(libs.mixinextras.common.get()) as Any)

	implementation("jarJar"(libs.mixinextras.forge.get()) as Any)

}

if (stonecutter.eval(stonecutter.current.version, "<1.20.5")) {
	renamer.mappings(minecraft.dependency.toSrg)
}

tasks.withType<JavaCompile>().configureEach {
	options.encoding = "UTF-8" // Use the UTF-8 charset for Java compilation
}

afterEvaluate {
	// ForgeGradle's run tasks only put getDefaultSourceSets() (main-only, or main+test for the
	// auto-generated per-sourceSet task variants) on the launch classpath; mods{} above does not
	// affect it. Adding testmod's compiled output directly to the auto-generated task's own (public,
	// standard Gradle) classpath is what actually gets it in front of FML's mod scanner.

//	tasks.named<JavaExec>("runTestmodClient") { classpath(sourceSets["test"].output) }
//	tasks.named<JavaExec>("runTestmodServer") { classpath(sourceSets["test"].output) }

	if (generateTests) {
		tasks.named<JavaExec>("runData") { classpath(sourceSets["gametest"].output) }
	}

	tasks.named<JavaExec>("runGameTestServer") {
		classpath(sourceSets["gametest"].output)
		if (generateTests) {
			dependsOn("runData")
		}
	}
	tasks.named<JavaExec>("runClient") {
		classpath(sourceSets["gametest"].output)
		if (generateTests) {
			dependsOn("runData")
		}
	}

	java.sourceSets["gametest"].resources { srcDir("src/gametest/generated/") }
}
