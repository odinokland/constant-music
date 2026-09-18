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

tasks.register("runActiveGameTest") {
	group = "stonecutter"
	description = "Run game tests of the active Stonecutter version"
	dependsOn(stonecutter.current!!.project + ":runGameTestServer")
}

tasks.register("testActive") {
	group = "stonecutter"
	description = "Run tests of the active Stonecutter version"
	dependsOn(stonecutter.current!!.project + ":test")
}

tasks.register("runAllTestsSequentially") {
	group = "verification"
	description = "Runs all unit tests and game tests across all Stonecutter variants sequentially to save memory."
	val variantProjects: List<Project>
	if (project.hasProperty("runTestsFor")) {
		val modLoader = project.property("runTestsFor") as String
		variantProjects = subprojects.filter { sub ->
			// Adjust this condition if you use a specific naming convention (e.g., contains("-fabric"))
			sub.name.endsWith("-${modLoader}") && sub.tasks.any { it.name == "test" || it.name == "runGameTestServer" }
		}
	} else {
		variantProjects = subprojects.filter { sub ->
			// Adjust this condition if you use a specific naming convention (e.g., contains("-fabric"))
			sub.name != "1.21.5-forge" && sub.tasks.any { it.name == "test" || it.name == "runGameTestServer" }
		}
	}

	doLast {
		logger.lifecycle("Found ${variantProjects.size} variants to test sequentially.")
	}

	var previousTask: Task? = null
	variantProjects.forEach { sub ->
		// Target standard unit tests
		val unitTestTask = sub.tasks.findByName("test")
		// Target Fabric/Forge/NeoForge game test tasks (adjust name if your loader uses a different task name)
		val gameTestTask = sub.tasks.findByName("runGameTestServer")

		listOfNotNull(unitTestTask, gameTestTask).forEach { currentTask ->
			if (previousTask != null) {
				// Force the current task to wait for the completion of the previous one
				currentTask.mustRunAfter(previousTask!!)
			}
			// Make the root aggregator task depend on this task
			dependsOn(currentTask)
			previousTask = currentTask
		}
	}
}

tasks.withType<Test>().configureEach {
	minHeapSize = "256m"
	maxHeapSize = "1024m" // Keep unit tests lean
	maxParallelForks = 1  // Force sequential forks within the subproject
}

stonecutter parameters {
	val currentLoader = current.project.substringAfterLast('-')
	constants.match(currentLoader, "fabric", "neoforge", "forge")
	constants["neoforge_game_annotations"] = (currentLoader == "neoforge" && eval(current.version, "<1.21.5"))
	swaps["mod_version"] = "\"${properties["mod.version"]}\";"
	swaps["mod_id"] = "\"${properties.get("mod.id")}\";"
	swaps["mod_name"] = "\"${properties.get("mod.name")}\";"
	swaps["mod_group"] = "\"${properties.get("mod.group")}\";"
	swaps["minecraft"] = "\"${current.version}\";"
	swaps["forge_modlist"] = when {
		eval(current.version, ">=26.1") -> "ModList;"
		else -> "ModList.get();"
	}
	swaps["gametest_annotation"] = when {
		(eval(current.version, ">=1.21.5") && currentLoader == "fabric") -> "@GameTest"
		(eval(current.version, ">=1.21.5")) -> "@GameTest(structure = TEMPLATE_NAME, environment = ENVIRONMENT_NAME)"
		else -> "@GameTest(template = TEMPLATE_NAME)"
	}
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

for (version in stonecutter.versions.map { it.version }.distinct()) tasks.register("publish$version") {
	group = "publishing"
	dependsOn(stonecutter.tasks.named("publishMods") { metadata.version == version })
}
