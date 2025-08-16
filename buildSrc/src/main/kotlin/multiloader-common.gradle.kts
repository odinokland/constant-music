plugins {
	id("java")
	id("idea")
	id("java-library")
}

version = "${loader}-${commonMod.version}+mc"

base {
	archivesName = commonMod.id
}

java {
	toolchain.languageVersion = JavaLanguageVersion.of(commonProject.prop("java.version")!!)
	// withSourcesJar()
	// withJavadocJar()
}

repositories {
    mavenCentral()
    exclusiveContent {
        forRepositories(
            maven("https://maven.parchmentmc.org") { name = "ParchmentMC" },
            maven("https://maven.neoforged.net/releases") { name = "NeoForge" },
        )
        filter { includeGroup("org.parchmentmc.data") }
    }
	maven("https://www.cursemaven.com")
	maven("https://api.modrinth.com/maven") {
		name = "Modrinth"
		content {
			includeGroup("maven.modrinth")
		}
	}
	maven("https://maven.terraformersmc.com/releases/") { name = "TerraformersMC" }
	maven("https://maven.quiltmc.org/repository/release")
	maven("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1")
	maven("https://nexus.velocitypowered.com/repository/maven-public")
}

tasks {

	processResources {
		duplicatesStrategy = DuplicatesStrategy.INHERIT
		val chosenJavaVersion = commonMod.propOrNull("java.version")
		val compatibilityVersion = commonMod.propOrNull("java.compatibility_version") ?: chosenJavaVersion
		val forgeMajorVersion: String? = commonMod.depOrNull("forge")?.substringBefore('.')
		val expandProps = mapOf(
			"javaVersion" to chosenJavaVersion,
			"javaCompatibilityVersion" to compatibilityVersion,
			"modId" to commonMod.id,
			"modName" to commonMod.name,
			"modVersion" to commonMod.version,
			"modGroup" to commonMod.group,
			"modAuthor" to commonMod.author,
			"modCredits" to commonMod.credits,
			"modDescription" to commonMod.description,
			"modLicense" to commonMod.license,
			"modLicenseIdentifier" to commonMod.licenseIdentifier,
			"modHomepage" to commonMod.propOrNull("homepage"),
			"modIssues" to commonMod.propOrNull("issues"),
			"modSources" to commonMod.propOrNull("sources"),
			"minecraftVersion" to commonMod.propOrNull("minecraft_version"),
			"minMinecraftVersion" to commonMod.propOrNull("min_minecraft_version"),
			"fabricLoaderVersion" to commonMod.depOrNull("fabric-loader"),
			"fabricApiVersion" to commonMod.depOrNull("fabric-api"),
			"forgeVersion" to commonMod.depOrNull("forge"),
			"forgeMajorVersion" to forgeMajorVersion,
			"neoForgeVersion" to commonMod.depOrNull("neoforge"),
			"modMenuVersion" to commonMod.depOrNull("modmenu"),
		).filterValues { it?.isNotEmpty() == true }.mapValues { (_, v) -> v!! }

		val jsonExpandProps = expandProps.mapValues { (_, v) -> v.replace("\n", "\\\\n") }

			filesMatching(listOf("META-INF/mods.toml", "META-INF/neoforge.mods.toml")) {
				expand(expandProps)
			}

		filesMatching(listOf("pack.mcmeta", "fabric.mod.json", "*.mixins.json*")) {
			expand(jsonExpandProps)
		}

		inputs.properties(expandProps)
	}
}

tasks.named("processResources") {
	dependsOn(":common:${commonMod.propOrNull("minecraft_version")}:stonecutterGenerate")
}
