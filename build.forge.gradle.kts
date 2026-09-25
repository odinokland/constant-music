import org.gradle.kotlin.dsl.register

plugins {
	id("mod-platform")
	id("net.minecraftforge.gradle")
	id("net.minecraftforge.renamer")
	id("net.minecraftforge.jarjar")
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

sourceSets.configureEach {
	val dir = layout.buildDirectory.dir("sourcesSets/$name")
	output.setResourcesDir(dir.get())
	java.destinationDirectory.set(dir)
}

jarJar.register() {
	archiveClassifier = null
}

minecraft {
	mappings("official", prop("deps.minecraft"))
	runs {
		configureEach {
//			if (stonecutter.eval(stonecutter.current.version, "<1.20.5 ")) {
//				environment("MOD_CLASSES", "{source_roots}")
//			}
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

if (stonecutter.eval(stonecutter.current.version, "<1.20.5 ")) {
	renamer {
		mappings(minecraft.dependency.toSrg)

		enableMixinRefmaps {
			config("${prop("mod.id")}.mixins.json")
			source(sourceSets["main"]) { refMap.set("${prop("mod.id")}.refmap.json") }
		}
		classes(tasks.named<Jar>("jarJar")) {
			output.set(tasks.named<Jar>("jar").get().archiveFile)
			dependsOn("jarJar")
			mappings(renamer.mixin.generatedMappings)
		}
	}
} else {
	tasks.withType<Jar>().configureEach {
		if (name == "sourcesJar") dependsOn("jarJar")
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

	//tasks.named<JavaExec>("runData") { classpath(sourceSets["gametest"].output) }
	tasks.named<JavaExec>("runGameTestServer") {
		classpath(sourceSets["gametest"].output)
		//dependsOn(":${ project.name }:runData")
	}
	tasks.named<JavaExec>("runClient") {
		classpath(sourceSets["gametest"].output)
		//dependsOn(":${ project.name }:runData")
	}

	//java.sourceSets[gametest.sourceSetName.get()].resources { srcDir("src/${ gametest.sourceSetName.get() }/generated/") }
}
