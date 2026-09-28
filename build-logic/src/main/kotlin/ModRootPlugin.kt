import dev.kikugie.stonecutter.controller.StonecutterControllerExtension
import me.modmuss50.mpp.ModPublishExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.getByType

class ModRootPlugin : Plugin<Project> {
	override fun apply(project: Project) {
		with(project) {
			val stonecutter = extensions.getByType<StonecutterControllerExtension>()
			val properties = stonecutter.properties
			val modVersion = properties.getAs<String>("mod.version")
			val channelTag = properties.getAs<String>("mod.channel_tag")
			val changelogText = file("CHANGELOG.md").takeIf { it.exists() }?.readText() ?: ""
			val githubToken = providers.environmentVariable("GITHUB_TOKEN")

			extensions.configure<ModPublishExtension>("publishMods") {
				dryRun = envTrue("PUB_DRY_RUN") || !envTrue("PUB_GITHUB_ENABLE")
				version = modVersion
				changelog.set(changelogText)
				type = releaseTypeFromChannelTag(channelTag)
				if (envTrue("PUB_GITHUB_ENABLE")) {
					github {
						accessToken = githubToken
						repository = providers.environmentVariable("GITHUB_REPOSITORY")
						commitish = providers.environmentVariable("GITHUB_SHA").orElse("main")
						tagName = providers.environmentVariable("GITHUB_REF_NAME")
						allowEmptyFiles = true
					}
				}
			}

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
				description =
					"Runs all unit tests and game tests across all Stonecutter variants sequentially to save memory."
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

			for (version in stonecutter.versions.map { it.version }.distinct()) tasks.register("publish$version") {
				group = "publishing"
				dependsOn(stonecutter.tasks.named("publishMods") { metadata.version == version })
			}
		}
	}
}
