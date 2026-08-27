@file:OptIn(dev.kikugie.stonecutter.StonecutterExperimentalAPI::class)

plugins {
	alias(libs.plugins.stonecutter)
	alias(libs.plugins.loom.back.compat).apply(false)
	alias(libs.plugins.neoforged.moddev).apply(false)
	alias(libs.plugins.jsonlang.postprocess).apply(false)
	alias(libs.plugins.mod.publish.plugin).apply(false)
	alias(libs.plugins.kotlin.jvm).apply(false)
	alias(libs.plugins.devtools.ksp).apply(false)
	alias(libs.plugins.fletching.table).apply(false)
	alias(libs.plugins.legacyforge.moddev).apply(false)
	alias(libs.plugins.forgegradle).apply(false)
	alias(libs.plugins.renamer).apply(false)
	alias(libs.plugins.jarjar).apply(false)
}

stonecutter active file(".sc_active_version")

tasks.register("runActiveClient") {
	group = "stonecutter"
	description = "Run client of the active Stonecutter version"
	dependsOn(stonecutter.current!!.project + ":runClient")
}

tasks.register("runActiveServer") {
	group = "stonecutter"
	description = "Run server of the active Stonecutter version"
	dependsOn(stonecutter.current!!.project + ":runServer")
}

stonecutter parameters {
	constants.match(current.project.substringAfterLast('-'), "fabric", "neoforge", "forge")
	swaps["mod_version"] = "\"${properties["mod.version"]}\";"
	swaps["mod_id"] = "\"${properties.get("mod.id")}\";"
	swaps["mod_name"] = "\"${properties.get("mod.name")}\";"
	swaps["mod_group"] = "\"${properties.get("mod.group")}\";"
	swaps["minecraft"] = "\"${current.version}\";"
	constants["release"] = properties.get("mod.id") != "modtemplate"

	replacements {
		string(current.parsed > "1.19.4") {
			replace("com.mojang.blaze3d.vertex.PoseStack","net.minecraft.client.gui.GuiGraphics")
		}
		string(current.parsed >= "1.21") {
			replace("net.minecraft.client.gui.screens.SoundOptionsScreen","net.minecraft.client.gui.screens.options.SoundOptionsScreen")
		}
		string(current.parsed >= "1.21.2", "level") {
			replace("LevelRenderer","LevelEventHandler")
		}
		string(current.parsed < "1.21.9" || current.parsed >= "1.21.2") {
			replace("net.minecraft.world.level.Level", "net.minecraft.client.multiplayer.ClientLevel")
		}
		string(current.parsed >= "1.21.6") {
			replace("net.minecraftforge.eventbus.api.SubscribeEvent", "net.minecraftforge.eventbus.api.listener.SubscribeEvent")
		}
	}
}

for (version in stonecutter.versions.map { it.version }.distinct()) tasks.register("publish$version") {
	group = "publishing"
	dependsOn(stonecutter.tasks.named("publishMods") { metadata.version == version })
}
