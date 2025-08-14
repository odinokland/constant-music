plugins {
	id("multiloader-common")
	id("fabric-loom")
	id("dev.kikugie.j52j") version "2.0"
}

loom {
	accessWidenerPath = common.project.file("../../src/main/resources/${mod.id}.accesswidener")

	mixin {
		useLegacyMixinAp = false
	}
}

dependencies {
	minecraft(group = "com.mojang", name = "minecraft", version = commonMod.mc)
	mappings(loom.layered {
		officialMojangMappings()
		commonMod.depOrNull("parchment")?.let { parchmentVersion ->
			parchment("org.parchmentmc.data:parchment-${commonMod.mc}:$parchmentVersion@zip")
		}
	})

    compileOnly("org.spongepowered:mixin:0.8.7")

    "io.github.llamalad7:mixinextras-common:0.5.0".let {
        compileOnly(it)
        annotationProcessor(it)
    }

	modCompileOnly("net.fabricmc:fabric-loader:${commonMod.dep("fabric-loader")}")
}

val commonJava: Configuration by configurations.creating {
	isCanBeResolved = false
	isCanBeConsumed = true
}

val commonResources: Configuration by configurations.creating {
	isCanBeResolved = false
	isCanBeConsumed = true
}

artifacts {
	afterEvaluate {
		val mainSourceSet = sourceSets.main.get()
		mainSourceSet.java.sourceDirectories.files.forEach {
			add(commonJava.name, it)
		}
		mainSourceSet.resources.sourceDirectories.files.forEach {
			add(commonResources.name, it)
		}
	}
}
// IntelliJ no longer downloads javadocs and sources by default, this tells Gradle to force IntelliJ to do it.
idea {
	module {
		isDownloadJavadoc = true
		isDownloadSources = true
	}
}