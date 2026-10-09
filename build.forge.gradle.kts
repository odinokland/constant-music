plugins {
	id("mod-platform")
	id("net.minecraftforge.gradle")
	id("net.minecraftforge.renamer")
	id("net.minecraftforge.jarjar")
}

stonecutter {
	swaps["gametest_annotation"] = when {
		(eval(current.version, ">=1.21.5")) -> "@GameTest(structure = TEMPLATE_NAME, environment = ENVIRONMENT_NAME)"
		(eval(current.version, ">1.20.1")) -> "@GameTest(template = TEMPLATE_NAME)"
		else -> "@GameTest(templateNamespace = Constants.MOD_ID, template = TEMPLATE_NAME)"
	}

	swaps["world_flows_var"] = when {
		(eval(current.version, "<1.20.4")) -> "@ModifyVariable(method = \"doLoadLevel(Lnet/minecraft/client/gui/screens/Screen;Ljava/lang/String;ZZZ)V\", at = @At(\"HEAD\"), ordinal = 3, argsOnly = true)"
		(eval(current.version, "<1.20.6")) -> "@ModifyVariable(method = \"loadLevel(Lnet/minecraft/world/level/storage/LevelStorageSource\$LevelStorageAccess;Lcom/mojang/serialization/Dynamic;ZZLjava/lang/Runnable;Z)V\", at = @At(\"HEAD\"), ordinal = 2, argsOnly = true)"
		(eval(current.version, "<1.21.11")) -> "@ModifyVariable(method = \"openWorldCheckWorldStemCompatibility(Lnet/minecraft/world/level/storage/LevelStorageSource\$LevelStorageAccess;Lnet/minecraft/server/WorldStem;Lnet/minecraft/server/packs/repository/PackRepository;Ljava/lang/Runnable;)V\", at = @At(value= \"STORE\"), ordinal = 1)"
		(eval(current.version, "<26.2")) -> "@ModifyVariable(method = \"openWorldCheckWorldStemCompatibility(Lnet/minecraft/world/level/storage/LevelStorageSource\$LevelStorageAccess;Lnet/minecraft/server/WorldStem;Lnet/minecraft/server/packs/repository/PackRepository;Ljava/lang/Runnable;)V\", at = @At(value= \"INVOKE\", target = \"Lcom/mojang/serialization/Lifecycle;stable()Lcom/mojang/serialization/Lifecycle;\"), ordinal = 1)"
		else -> "@ModifyVariable(method = \"openWorldCheckWorldStemCompatibility(Lnet/minecraft/world/level/storage/LevelStorageSource\$LevelStorageAccess;Lnet/minecraft/server/WorldStem;Lnet/minecraft/server/packs/repository/PackRepository;Ljava/lang/Runnable;)V\", at = @At(value= \"INVOKE\", target = \"Lcom/mojang/serialization/Lifecycle;stable()Lcom/mojang/serialization/Lifecycle;\"), ordinal = 1, require = 0)"
	}

	swaps["world_flows_inject"] = when {
		(eval(current.version, "<1.20.6")) -> "@Inject(method = \"confirmWorldCreation\", at = @At(value = \"INVOKE_ASSIGN\", target = \"Lcom/mojang/serialization/Lifecycle;experimental()Lcom/mojang/serialization/Lifecycle;\"), cancellable = true)"
		(eval(current.version, "<26.2")) -> "@Inject(method = \"confirmWorldCreation\", at = @At(value = \"INVOKE_ASSIGN\", target = \"Lcom/mojang/serialization/Lifecycle;experimental()Lcom/mojang/serialization/Lifecycle;\", remap = false), cancellable = true)"
		else -> "@Inject(method = \"confirmWorldCreation\", at = @At(value = \"INVOKE_ASSIGN\", target = \"Lcom/mojang/serialization/Lifecycle;experimental()Lcom/mojang/serialization/Lifecycle;\", remap = false), cancellable = true, require = 0)"
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
if (stonecutter.eval(stonecutter.current.version, "<1.21.10")) {
	jarJar {
		register("jarJar") {
			if (stonecutter.eval(stonecutter.current.version, ">=1.20.6")) {
				archiveClassifier.set(null)
			}
		}
	}
	tasks.named<Jar>("jar") {
		enabled = false
	}
}

val generateTests = stonecutter.eval(stonecutter.current.version, ">= 1.21.5")

minecraft {
	if (stonecutter.eval(stonecutter.current.version, "<=1.21.11")) {
		mappings("official", prop("deps.minecraft"))
	}
	val devJvmArgs = propsList("mod", "dev_jvm_args") +
		propsList("mod", "dev_jvm_args_mixin_debug")
	runs {
		configureEach {
			if (stonecutter.eval(stonecutter.current.version, "<1.20.5 ")) {
				environment("MOD_CLASSES", "{source_roots}")
			}
			workingDir.convention(layout.projectDirectory.dir("run"))

			systemProperty("eventbus.api.strictRuntimeChecks", true)
			systemProperty("forge.enabledGameTestNamespaces", "${prop("mod.id")},${prop("mod.gametest.id")}")
			args("--mixin.config", "${prop("mod.id")}.mixins.json")
			mods {
				create(prop("mod.id")) {
					source(sourceSets["main"])
				}
				create(prop("mod.gametest.id")) {
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
					prop("mod.gametest.id"),
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
	if (stonecutter.eval(stonecutter.current.version, "<1.20.6")) {
		val mixinProcessor =
			"org.spongepowered:mixin:${libs.versions.mixin.get()}:processor"

		annotationProcessor(mixinProcessor)
		//"gametestAnnotationProcessor"(mixinProcessor)
	}
	compileOnly(annotationProcessor(libs.mixinextras.common.get()) as Any)
	implementation(libs.mixinextras.forge.get())

	// 1.21.10 bundles mixin extras
	if (stonecutter.eval(stonecutter.current.version, "<1.21.10")) {
		add("jarJar", libs.mixinextras.forge.get())
	}
//	"gametestCompileOnly"(sourceSets["main"].output)

}
if (stonecutter.eval(stonecutter.current.version, "<1.20.6")) {
	renamer {
		mappings(minecraft.dependency.toSrg)

		enableMixinRefmaps {
			config("${prop("mod.id")}.mixins.json")
			source(project.sourceSets.main.get()) {
				refMap = "${prop("mod.id")}.refmap.json"
			}

			jar(tasks.named<Jar>("jarJar"))
		}
		classes(tasks.named<Jar>("jarJar")) {
			mappings(renamer.mixin.generatedMappings)
			archiveClassifier.set(null)
		}
		classes(tasks.named<Jar>("gametestJar")) {
			archiveClassifier.set("gametest")
		}
	}

	tasks.named("mergeMixinMappings") {
		dependsOn("jarJar")
	}
	tasks.named<Jar>("gametestJar") {
		archiveClassifier.set("gametest-noobf")
	}
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
