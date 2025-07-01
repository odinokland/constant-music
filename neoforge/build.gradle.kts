import net.neoforged.moddevgradle.dsl.NeoForgeExtension as NeoForge
import net.neoforged.moddevgradle.legacyforge.dsl.LegacyForgeExtension as LegacyForge

plugins {
	`multiloader-loader`
	id("dev.kikugie.j52j") version "2.0"
}

var configBlock: Any? = null

if (stonecutter.eval(stonecutter.current.version, ">=1.20.2")) {
	apply(plugin = "net.neoforged.moddev")

	extensions.configure(NeoForge::class) {
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
} else {
	apply(plugin ="net.neoforged.moddev.legacyforge")

	extensions.configure(LegacyForge::class) {

		version = commonMod.dep("neoforge")
		//accessTransformers.from(project.file("../../src/main/resources/META-INF/accesstransformer.cfg").absolutePath)

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
}

dependencies {

}

sourceSets.main {
	resources.srcDir("src/generated/resources")
}

tasks {
	processResources {
		exclude("${mod.id}.accesswidener")
	}
}