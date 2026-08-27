plugins {
	id("mod-platform")
	id("dev.kikugie.loom-back-compat")
}

stonecutter {
	val (version, loader) = current.project.split('-', limit = 2)
	properties.tags(version, loader)

	replacements.string(current.parsed >= "1.21.11") {
		replace("ResourceLocation", "Identifier")
		replace("location()", "identifier()")
	}
	replacements.string(current.parsed >= "26.1.2") {
		replace("FabricDataOutput", "FabricPackOutput")
	}
}

platform {
	loader = "fabric"
	dependencies {
		required("minecraft") {
			fabricLikeVersionRange = prop("deps.minecraft")
		}
		required("fabric-api") {
			slug("fabric-api")
			fabricLikeVersionRange = ">=${prop("deps.fabric-api")}"
		}
		required("fabricloader") {
			fabricLikeVersionRange = ">=${prop("deps.fabric-loader")}"
		}
		optional("modmenu") {}
	}
}

loom {
	accessWidenerPath = getAccessFile(AccessType.WIDENER)
	runs.named("client") {
		client()
		generateRunConfig.set(false)
		runDirectory.set(layout.projectDirectory.dir("run"))
		runtimeEnvironment.set("client")
		programArguments.addAll("--username","Dev")
		displayName.set("Fabric Client")
	}
	runs.named("server") {
		server()
		generateRunConfig.set(false)
		runDirectory.set(layout.projectDirectory.dir("run"))
		runtimeEnvironment.set("server")
		displayName.set("Fabric Server")
	}
}

fabricApi {
//	configureDataGeneration {
//		outputDirectory = file("${rootDir}/versions/datagen/${sc.current.version.split("-")[0]}/src/main/generated")
//		client = true
//	}
}

repositories {
	mavenCentral()
	strictMaven("https://maven.terraformersmc.com/", "com.terraformersmc") { name = "TerraformersMC" }
	strictMaven("https://api.modrinth.com/maven", "maven.modrinth") { name = "Modrinth" }
}

configurations.all {
	resolutionStrategy {
		force("net.fabricmc:fabric-loader:${prop("deps.fabric-loader")}")
	}
}

dependencies {
	minecraft("com.mojang:minecraft:${prop("deps.minecraft")}")
	if (sc.current.parsed < "26") {
		mappings(loom.layered {
			officialMojangMappings()
			if (hasProperty("deps.parchment"))
				parchment("org.parchmentmc.data:parchment-${prop("deps.parchment")}@zip")
		})
	}
	annotationProcessor("net.fabricmc:sponge-mixin:0.17.2+mixin.0.8.7")
	include(implementation(annotationProcessor("io.github.llamalad7:mixinextras-fabric:${libs.versions.mixinextras.get()}")!!)!!)
	modImplementation("net.fabricmc:fabric-loader:${prop("deps.fabric-loader")}")
	// implementation(libs.moulberry.mixinconstraints)
	// include(libs.moulberry.mixinconstraints)
	modImplementation("net.fabricmc.fabric-api:fabric-api:${prop("deps.fabric-api")}")
	modImplementation("com.terraformersmc:modmenu:${prop("deps.modmenu")}")
	//ksp("org.spongepowered:mixin:0.8.7:processor")
}
