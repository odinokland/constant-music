import org.gradle.kotlin.dsl.version

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
			forgeLikeVersionRange = prop("deps.minecraft")
		}
		required("forge") {
			forgeLikeVersionRange.set("[1,)")
		}
	}
}

jarJar.register() {
	archiveClassifier = null
}

minecraft {
	mappings("official", prop("deps.minecraft"))
	runs {
		configureEach {
			if (stonecutter.eval(stonecutter.current.version, "<1.20.5 ")) {
				environment("MOD_CLASSES", "{source_roots}")
			}
			workingDir.convention(layout.projectDirectory.dir("run"))

			systemProperty("eventbus.api.strictRuntimeChecks", true)
			systemProperty("forge.enabledGameTestNamespaces", prop("mod.id"))
			args("--mixin.config", "${prop("mod.id")}.mixins.json")
			if (stonecutter.eval(stonecutter.current.version, ">=1.17") && stonecutter.eval(stonecutter.current.version, "<=1.18")) {
				jvmArgs("--add-opens=java.base/java.lang.invoke=ALL-UNNAMED")
			}
		}
		register("client") {
			with(sourceSets["main"]) {
				mods {
					create(prop("mod.id")) {
						source(sourceSets["main"])
					}
				}
			}
			systemProperty("forge.logging.console.level", "debug")
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
	//tasks.named("mergeMixinMappings") { dependsOn("compileJava") }
//	tasks.withType<Jar>().configureEach {
//		if (name == "sourcesJar") dependsOn("renameJar")
//	}
} else {
	tasks.withType<Jar>().configureEach {
		if (name == "sourcesJar") dependsOn("jarJar")
	}
}

// ForgeGradle 7.0.35 derives this task output from its own outputDirectory.
// With Gradle 9, that self-referential provider can leave the launcher metadata
// stale or incomplete (notably omitting module-path dependencies such as
// jopt-simple). Keep the generated runs.json at a stable, explicit location.
tasks.configureEach {
	if (name == "slimeLauncherMetadataForForge") {
		@Suppress("UNCHECKED_CAST")
		val outputDirectory = javaClass.methods
			.first { it.name == "getOutputDirectory" }
			.invoke(this) as org.gradle.api.file.DirectoryProperty
		@Suppress("UNCHECKED_CAST")
		val runsJson = javaClass.methods
			.first { it.name == "getRunsJson" }
			.invoke(this) as org.gradle.api.file.RegularFileProperty
		val metadataDirectory = layout.buildDirectory.dir("forgegradle/slimeLauncherMetadata")
		outputDirectory.set(metadataDirectory)
		runsJson.set(metadataDirectory.map { it.file("launcher/runs.json") })
	}
}

tasks.withType<JavaCompile>().configureEach {
	options.encoding = "UTF-8" // Use the UTF-8 charset for Java compilation
}
