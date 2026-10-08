import dev.kikugie.stonecutter.controller.flag.StonecutterFlag


plugins {
	alias(libs.plugins.mod.publish.plugin)
	//alias(libs.plugins.loom.back.compat).apply(false)
	id("dev.kikugie.loom-back-compat").apply(false)
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
		swaps["mod_gametest_id"] = "\"${properties.get("mod.gametest.id")}\";"
		swaps["minecraft"] = "\"${current.version}\";"
		swaps["gametest_implementations"] = "{"
		swaps["render_input"] = when {
			eval(current.version, ">=26.1") -> "GuiGraphicsExtractor guiGraphics,"
			eval(current.version, ">1.19.4") -> "GuiGraphics guiGraphics,"
			else -> "PoseStack guiGraphics,"
		}
		swaps["world_flows_var"] = "@ModifyVariable(method = \"openWorldCheckWorldStemCompatibility(Lnet/minecraft/world/level/storage/LevelStorageSource\$LevelStorageAccess;Lnet/minecraft/server/WorldStem;Lnet/minecraft/server/packs/repository/PackRepository;Ljava/lang/Runnable;)V\", at = @At(value= \"STORE\"), ordinal = 1)"
		swaps["world_flows_inject"] = "@Inject(method = \"confirmWorldCreation\", at = @At(value = \"INVOKE_ASSIGN\", target = \"Lcom/mojang/serialization/Lifecycle;experimental()Lcom/mojang/serialization/Lifecycle;\", remap = false), cancellable = true)"

		constants["release"] = properties.get("mod.id") != "modtemplate"

		replacements {
			string(current.parsed > "1.19.4") {
				replace("com.mojang.blaze3d.vertex.PoseStack","net.minecraft.client.gui.GuiGraphics")
				replace("drawCenteredString(guiGraphics, ", "guiGraphics.drawCenteredString(")
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
 			replace("net.minecraft.client.gui.GuiGraphics", "net.minecraft.client.gui.GuiGraphicsExtractor")
 			replace("abstractWidget.render", "abstractWidget.extractRenderState")
 			replace("renderContent", "extractContent")
 			replace("public void render(", "public void extractRenderState(")
 			replace("renderBackground(", "extractBackground(")
 			replace("super.render(", "super.extractRenderState(")
 		}
			string(current.parsed >= "26.1", "forge_update") {
				replace("ModList.get()", "ModList")
			}

		}
	}
}
