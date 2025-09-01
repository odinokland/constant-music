plugins {
	`multiloader-loader`
	id("net.minecraftforge.gradle") version "[6.0.24,6.2)"
	id("org.spongepowered.mixin") version "0.7-SNAPSHOT"
	id("dev.kikugie.j52j") version "2.0"
}

// Ensure the ForgeGradle Jar-in-Jar extension is enabled in Kotlin DSL
extensions.configure<net.minecraftforge.gradle.userdev.jarjar.JarJarProjectExtension>("jarJar") {
	enable()
}

java {
	val java = if (stonecutter.eval(stonecutterBuild.current.version, ">=1.20.5"))
		JavaVersion.VERSION_21 else JavaVersion.VERSION_17
	targetCompatibility = java
	sourceCompatibility = java
}

minecraft {
	mappings("official", commonMod.mc)

	copyIdeResources = true //Calls processResources when in dev
	if (stonecutter.eval(stonecutterBuild.current.version, ">=1.20.6")) {
		reobf = false // Forge 1.20.6+ uses official mappings at runtime, so we shouldn't reobf from official to SRG
	} else {
		reobf = true
	}

	// Automatically enable forge AccessTransformers if the file exists
	// This location is hardcoded in Forge and can not be changed.
	// https://github.com/MinecraftForge/MinecraftForge/blob/be1698bb1554f9c8fa2f58e32b9ab70bc4385e60/fmlloader/src/main/java/net/minecraftforge/fml/loading/moddiscovery/ModFile.java#L123
	// Forge still uses SRG names during compile time, so we cannot use the common AT's
	val at = file("src/main/resources/META-INF/accesstransformer.cfg")
	if (at.exists()) {
		accessTransformer(at)
	}

	runs {
		create("client") {
			workingDirectory(file("run"))
			ideaModule("${rootProject.name}.${loader}.${project.name}.main")
			taskName("Client")
			if (stonecutter.eval(stonecutterBuild.current.version, ">=1.21.6")) {
				property("eventbus.api.strictRuntimeChecks", "true")
			}

			mods {
				create(commonMod.id) {
					source(sourceSets.main.get())
				}
			}
		}
//		create("gameTestServer") {
//			workingDirectory(file("run"))
//			ideaModule("${rootProject.name}.${loader}.${project.name}.main")
//			taskName("Test")
//			if (stonecutter.eval(stonecutterBuild.current.version, ">=1.21.6")) {
//				property("eventbus.api.strictRuntimeChecks", "true")
//			}
//			// Recommended logging data for a userdev environment
//			// The markers can be added/remove as needed separated by commas.
//			// "SCAN": For mods scan.
//			// "REGISTRIES": For firing of registry events.
//			// "REGISTRYDUMP": For getting the contents of all registries.
//			property("forge.logging.markers", "REGISTRIES")
//
//			// Recommended logging level for the console
//			// You can set various levels here.
//			// Please read: https://stackoverflow.com/questions/2031163/when-to-use-the-different-log-levels
//			property("forge.logging.console.level", "debug")
//
//			// Comma-separated list of namespaces to load gametests from. Empty = all namespaces.
//			property("forge.enabledGameTestNamespaces", commonMod.id)
//
//			mods {
//				create(commonMod.id) {
//					source(sourceSets.main.get())
//				}
//			}
//		}
	}
}

dependencies {
	// Required dependencies
	minecraft("net.minecraftforge:forge:${commonMod.mc}-${commonMod.dep("forge")}")
	annotationProcessor("org.spongepowered:mixin:0.8.7:processor")
	compileOnly("io.github.llamalad7:mixinextras-common:0.5.0") {
		annotationProcessor(this)
	}

	runtimeOnly("io.github.llamalad7:mixinextras-forge:0.5.0")
	// 1) Create the dependency on the jarJar configuration
	val mixinExtras = jarJar("io.github.llamalad7:mixinextras-forge:0.5.0")
	// 2) Attach version range to the SAME dependency instance
	if (mixinExtras != null) {
		jarJar
			.ranged(mixinExtras, "[0.5.0,)")
		// 3) Put it on a normal configuration so your mod can compile/run against it
		implementation(mixinExtras)
	}

	if (stonecutter.eval(stonecutterBuild.current.version, ">=1.21.6")) {
	annotationProcessor("net.minecraftforge:eventbus-validator:7.0-beta.7")
	}
	// Forge's hack fix
	implementation("net.sf.jopt-simple:jopt-simple:5.0.4") { version { strictly("5.0.4") } }
}

mixin {
	add(sourceSets.main.get(), "constantmusic.refmap.json")
	config("${commonMod.id}.common.mixins.json")
	config("${commonMod.id}.forge.mixins.json")
}

sourceSets.main {
	//ext.set("refMap", "constantmusic.refmap.json")
	resources.srcDir("src/generated/resources")
}

tasks {
	processResources {
		exclude("${commonMod.id}.accesswidener")
	}

	register("ideaSyncTask", Copy::class) {
		dependsOn("genIntellijRuns")
	}

	jar {
		manifest.attributes(
			"Specification-Title" to commonMod.id,
			"Specification-Vendor" to commonMod.author,
			"Specification-Version" to 1,
			"Implementation-Title" to commonMod.name,
			"Implementation-Version" to commonMod.version,
			"Implementation-Vendor" to commonMod.author,
			"MixinConfigs" to "${commonMod.id}.common.mixins.json,${commonMod.id}.forge.mixins.json"
		)
		if (stonecutter.eval(stonecutterBuild.current.version, "<1.20.6")) {
			finalizedBy("reobfJar")
		}
		archiveClassifier.set("thin")
	}

	named<org.gradle.jvm.tasks.Jar>("jarJar") {
		archiveClassifier.set("")
	}
}

// IntelliJ no longer downloads javadocs and sources by default, this tells Gradle to force IntelliJ to do it.
idea {
	module {
		isDownloadJavadoc = true
		isDownloadSources = true
	}
}

sourceSets.forEach {
	val dir = layout.buildDirectory.dir("sourcesSets/${it.name}")
	it.output.setResourcesDir(dir)
	it.java.destinationDirectory.set(dir)

}


artifacts {
	archives(tasks.named("jarJar"))
	archives(tasks.named("jar"))
}

if (stonecutter.eval(stonecutterBuild.current.version, "<1.20.6")) {
	// Workaround for SpongePowered/MixinGradle#38
	afterEvaluate {
		tasks.named("configureReobfTaskForReobfJar").configure {
			mustRunAfter(tasks.named("compileJava"))
		}
		tasks.named("configureReobfTaskForReobfJarJar").configure {
			mustRunAfter(tasks.named("compileJava"))
		}
	}
}