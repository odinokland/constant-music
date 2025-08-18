import net.neoforged.moddevgradle.dsl.NeoForgeExtension as NeoForge
import net.neoforged.moddevgradle.legacyforge.dsl.LegacyForgeExtension as LegacyForge

plugins {
	`java-library`
	idea
	`multiloader-loader`
	id("dev.kikugie.j52j") version "2.0"
	id("net.neoforged.moddev")
}

neoForge{
	enable {
		version = commonMod.dep("neoforge")
	}
	accessTransformers.from(project.file("../../src/main/resources/META-INF/accesstransformer.cfg").absolutePath)

	runs {
		register("client") {
			client()
			ideName = "NeoForge Client (${project.path})"
		}
	}

	parchment {
		commonMod.depOrNull("parchment")?.let {
			mappingsVersion = it
			minecraftVersion = commonMod.mc
		}
	}

	mods {
		register(commonMod.id) {
			sourceSet(sourceSets.main.get())
		}
	}

}

dependencies {
	jarJar("io.github.llamalad7:mixinextras-neoforge:0.5.0")?.let { implementation(it) }
}

sourceSets.main {
	resources.srcDir("src/generated/resources")
}

tasks {
	processResources {
		exclude("${mod.id}.accesswidener")
	}
	jar {
		archiveFileName.set("${commonMod.id}-neoforge-${commonMod.version}+mc${commonMod.mc}.jar")
	}
}

tasks.named("createMinecraftArtifacts") {
	dependsOn(":neoforge:${commonMod.propOrNull("minecraft_version")}:stonecutterGenerate")
}

// IntelliJ no longer downloads javadocs and sources by default, this tells Gradle to force IntelliJ to do it.
idea {
	module {
		isDownloadJavadoc = true
		isDownloadSources = true
	}
}