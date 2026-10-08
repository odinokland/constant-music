plugins {
	id("mod-platform")
	id("dev.kikugie.loom-back-compat")
}

stonecutter {
	val (version, loader) = current.project.split('-', limit = 2)
	properties.tags(version, loader)

	swaps["gametest_implementations"] = when {
		(eval(current.version, ">=1.21.5")) -> "implements CustomTestMethodInvoker {"
		else -> "implements FabricGameTest {"
	}

	swaps["gametest_annotation"] = when {
		(eval(current.version, ">=1.21.5")) -> "@GameTest"
		else -> "@GameTest"
	}

	swaps["world_flows_var"] = when {
		(eval(current.version, "< 1.20.4")) -> "@ModifyVariable(method = \"doLoadLevel\", at = @At(value= \"STORE\"), ordinal = 3)"
		(eval(current.version, "< 1.20.6")) -> "@ModifyVariable(method = \"loadLevel\", at = @At(value= \"STORE\"), ordinal = 3)"
		else -> "@ModifyVariable(method = \"openWorldCheckWorldStemCompatibility(Lnet/minecraft/world/level/storage/LevelStorageSource\$LevelStorageAccess;Lnet/minecraft/server/WorldStem;Lnet/minecraft/server/packs/repository/PackRepository;Ljava/lang/Runnable;)V\", at = @At(value= \"STORE\"), ordinal = 1)"
	}

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
			fabricLikeVersionRange = "^${prop("deps.minMinecraft")}"
		}
		required("fabric-api") {
			slug("fabric-api")
			fabricLikeVersionRange = ">=${prop("deps.fabricApi")}+${prop("deps.minecraft")}"
		}
		required("fabricloader") {
			fabricLikeVersionRange = ">=${prop("deps.fabric-loader")}"
		}
		optional("modmenu") {}
	}
}

loom {
	accessWidenerPath = getAccessFile(AccessType.WIDENER)
	val devJvmArgs = propsList("mod", "dev_jvm_args") +
		propsList("mod", "dev_jvm_args_mixin_debug")
	if (sc.current.parsed < "26") {
		mixin {
			useLegacyMixinAp = true
			defaultRefmapName = "${prop("mod.id")}.refmap.json"
		}
	}
	runs.named("client") {
		client()
		generateRunConfig.set(false)
		runDirectory.set(layout.projectDirectory.dir("run/client"))
		programArguments.addAll("--username","Dev")
		jvmArguments.addAll(devJvmArgs)
		displayName.set("Fabric Client")
	}
	runs.named("server") {
		server()
		generateRunConfig.set(false)
		runDirectory.set(layout.projectDirectory.dir("run/server"))
		jvmArguments.addAll(devJvmArgs)
		displayName.set("Fabric Server")
	}
	runs.register("gameTestServer") {
		server()
		generateRunConfig.set(false)
		runDirectory.set(layout.projectDirectory.dir("run/server"))
		sourceSet.set("gametest")
		jvmArguments.addAll(devJvmArgs)
		displayName.set("Fabric GameTest Server")
		systemProperties.put("fabric-api.gametest", "true")
		systemProperties.put("fabric-api.gametest.report-file", layout.buildDirectory.file("gametest-report.xml").get().asFile.absolutePath)
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
		annotationProcessor("net.fabricmc:sponge-mixin:0.17.4+mixin.0.8.7")
	}
	include(implementation(annotationProcessor("io.github.llamalad7:mixinextras-fabric:${libs.versions.mixinextras.get()}")!!)!!)
	modImplementation("net.fabricmc:fabric-loader:${prop("deps.fabric-loader")}")
	// implementation(libs.moulberry.mixinconstraints)
	// include(libs.moulberry.mixinconstraints)
	modImplementation("net.fabricmc.fabric-api:fabric-api:${prop("deps.fabricApi")}+${prop("deps.minecraft")}")
	modImplementation("com.terraformersmc:modmenu:${prop("deps.modmenu")}")
	//ksp("org.spongepowered:mixin:0.8.7:processor")
}
