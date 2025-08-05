plugins {
	`multiloader-loader`
	id("net.minecraftforge.gradle") version "[6.0.24,6.2)"
	id("org.spongepowered.mixin") version "0.7-SNAPSHOT"
	id("dev.kikugie.j52j") version "2.0"
}

minecraft {
	mappings("official", commonMod.mc)

	copyIdeResources = true //Calls processResources when in dev

	reobf = false // Forge 1.20.6+ uses official mappings at runtime, so we shouldn"t reobf from official to SRG

	// Automatically enable forge AccessTransformers if the file exists
	// This location is hardcoded in Forge and can not be changed.
	// https://github.com/MinecraftForge/MinecraftForge/blob/be1698bb1554f9c8fa2f58e32b9ab70bc4385e60/fmlloader/src/main/java/net/minecraftforge/fml/loading/moddiscovery/ModFile.java#L123
	// Forge still uses SRG names during compile time, so we cannot use the common AT"s
	val at = file("src/main/resources/META-INF/accesstransformer.cfg")
	if (at.exists()) {
		accessTransformer(at)
	}

	runs {
		create("client") {
			workingDirectory(file("run"))
			ideaModule("${rootProject.name}.${loader}.${project.name}.main")
			taskName("Client")
			//? if >=1.21.6 {
			property("eventbus.api.strictRuntimeChecks", "true")
			//? }
			mods {
				create(commonMod.id) {
					source(sourceSets.main.get())
				}
			}
		}
	}
}

dependencies {
	// Required dependencies
	minecraft("net.minecraftforge:forge:${commonMod.mc}-${commonMod.dep("forge")}")
	annotationProcessor("org.spongepowered:mixin:0.8.7:processor")
	annotationProcessor("io.github.llamalad7:mixinextras-common:0.5.0")?.let { compileOnly(it) }
	jarJar("io.github.llamalad7:mixinextras-forge:0.5.0")?.let { implementation(it) }.let {
		jarJar.ranged(it, "[0.5.0,)")
	}
	//? if >= 1.21.6 {
	annotationProcessor("net.minecraftforge:eventbus-validator:7.0-beta.7")
	// }
	// Forge's hack fix
	implementation("net.sf.jopt-simple:jopt-simple:5.0.4") { version { strictly("5.0.4") } }
}

mixin {
	config("${commonMod.id}.common.mixins.json")
	config("${commonMod.id}.forge.mixins.json")
}

sourceSets.main {
	ext.set("refMap", "${commonMod.id}.refmap.json")
	resources.srcDir("src/generated/resources")
}

sourceSets.forEach {
	val dir = layout.buildDirectory.dir("sourcesSets/${it.name}")
	it.output.setResourcesDir(dir)
	it.java.destinationDirectory.set(dir)

}

tasks {
	processResources {
		exclude("${commonMod.id}.accesswidener")
	}

	register("ideaSyncTask", Copy::class) {
		dependsOn("genIntellijRuns")
	}
}

// IntelliJ no longer downloads javadocs and sources by default, this tells Gradle to force IntelliJ to do it.
idea {
	module {
		isDownloadJavadoc = true
		isDownloadSources = true
	}
}

//publishing {
//	publications {
//		mavenJava(MavenPublication) {
//			fg.component(it)
//		}
//	}
//}