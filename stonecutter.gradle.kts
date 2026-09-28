@file:OptIn(dev.kikugie.stonecutter.StonecutterExperimentalAPI::class)

import dev.kikugie.stonecutter.controller.flag.StonecutterFlag


plugins {
	alias(libs.plugins.stonecutter)
	alias(libs.plugins.mod.publish.plugin)
	alias(libs.plugins.loom.back.compat).apply(false)
	alias(libs.plugins.neoforged.moddev).apply(false)
	alias(libs.plugins.jsonlang.postprocess).apply(false)
	alias(libs.plugins.kotlin.jvm).apply(false)
	alias(libs.plugins.devtools.ksp).apply(false)
	alias(libs.plugins.fletching.table).apply(false)
	alias(libs.plugins.legacyforge.moddev).apply(false)
	alias(libs.plugins.forgegradle).apply(false)
	alias(libs.plugins.renamer).apply(false)
	alias(libs.plugins.jarjar).apply(false)
	id("mod-root")
}

tasks.withType<Test>().configureEach {
	minHeapSize = "256m"
	maxHeapSize = "1024m" // Keep unit tests lean
	maxParallelForks = 1  // Force sequential forks within the subproject
}

stonecutter {
	active(file(".sc_active_version"))
	flags {
		set(StonecutterFlag.LINE_SEPARATOR, "\n")
	}
	tasks {
		order("publishModrinth")
		order("publishCurseforge")
	}
	parameters {
		val currentLoader = current.project.substringAfterLast('-')
		constants.match(currentLoader, "fabric", "neoforge", "forge")

		swaps["mod_version"] = "\"${properties["mod.version"]}\";"
		swaps["mod_id"] = "\"${properties.get("mod.id")}\";"
		swaps["mod_name"] = "\"${properties.get("mod.name")}\";"
		swaps["mod_group"] = "\"${properties.get("mod.group")}\";"
		swaps["minecraft"] = "\"${current.version}\";"
		swaps["forge_modlist"] = when {
			eval(current.version, ">=26.1") -> "ModList;"
			else -> "ModList.get();"
		}
		swaps["gametest_implementations"] = "{"

		constants["release"] = properties.get("mod.id") != "modtemplate"

		replacements {
			string(current.parsed > "1.19.4") {
				replace("com.mojang.blaze3d.vertex.PoseStack","net.minecraft.client.gui.GuiGraphics")
			}
			string(current.parsed >= "1.21") {
				replace("net.minecraft.client.gui.screens.SoundOptionsScreen","net.minecraft.client.gui.screens.options.SoundOptionsScreen")
			}
			string(current.parsed >= "1.21.2", "level_renderer") {
				replace("LevelRenderer","LevelEventHandler")
			}
			string(current.parsed >="1.21.9" || current.parsed <"1.21.2", "level_import") {
				replace("net.minecraft.world.level.Level", "net.minecraft.client.multiplayer.ClientLevel")
			}
			string(current.parsed >="1.21.9" || current.parsed <"1.21.2", "level_name") {
				replace("Level", "ClientLevel")
			}
			string(current.parsed >= "1.21.5") {
				replace("GameTestHolder", "GameTestNamespace")
			}

			string(current.parsed >= "1.21.6") {
				replace("net.minecraftforge.eventbus.api.SubscribeEvent", "net.minecraftforge.eventbus.api.listener.SubscribeEvent")
			}
			string(current.parsed >= "26.1") {
				replace("GuiGraphics", "GuiGraphicsExtractor")
				replace("net.minecraft.client.gui.GuiGraphics", "net.minecraft.client.gui.GuiGraphicsExtractor")
				replace("abstractWidget.render", "abstractWidget.extractRenderState")
				replace("renderContent", "extractContent")
			}
			string(current.parsed >= "26.1", "forge_update") {
				replace("ModList.get()", "ModList")
			}

		}
	}
}
