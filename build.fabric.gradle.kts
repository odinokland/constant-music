import net.fabricmc.loom.task.RemapJarTask

plugins {
	id("mod-platform")
	id("dev.kikugie.loom-back-compat")
}

stonecutter {
	swaps["gametest_implementations"] = when {
		(eval(current.version, ">=1.21.5")) -> "implements CustomTestMethodInvoker {"
		else -> "implements FabricGameTest {"
	}

	swaps["gametest_annotation"] = when {
		(eval(current.version, ">=1.21.5")) -> "@GameTest(structure = TEMPLATE_NAME)"
		else -> "@GameTest(template = EMPTY_STRUCTURE)"
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
			fabricLikeVersionRange = ">=${prop("deps.minFabricApi")}"
		}
		required("fabricloader") {
			fabricLikeVersionRange = "*"
		}
		optional("modmenu") {}
	}
}

val remap = (sc.current.parsed < "26")

loom {
	accessWidenerPath = getAccessFile(AccessType.WIDENER)
	val devJvmArgs = propsList("mod", "dev_jvm_args") +
		propsList("mod", "dev_jvm_args_mixin_debug")
	if (sc.current.parsed < "26") {
		mixin {
			useLegacyMixinAp = true
			defaultRefmapName = "${prop("mod.id")}.refmap.json"
		}
		createRemapConfigurations(sourceSets["gametest"])
	}

	mods {
		create(prop("mod.id")) {
			sourceSet(sourceSets["main"])
		}
		create(prop("mod.gametest.id")) {
			sourceSet(sourceSets["gametest"])
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
//		outputDirectory = file("${rootDir}/versions/datagen/${sc.current.version}/src/main/generated")
//		client = true
//	}
}



repositories {
	mavenCentral()
	strictMaven("https://maven.terraformersmc.com/", "com.terraformersmc") { name = "TerraformersMC" }
	strictMaven("https://api.modrinth.com/maven", "maven.modrinth") { name = "Modrinth" }
	maven {
		name = "3arthqu4ke's Maven"
		url = uri("https://3arthqu4ke.github.io/maven")
	}
}

configurations {
	all {
		resolutionStrategy {
			force("net.fabricmc:fabric-loader:${prop("deps.fabric-loader")}")
		}
	}
}

configurations.named("gametestCompileClasspath") {
	extendsFrom(configurations.named("compileClasspath").get())
}
configurations.named("gametestRuntimeClasspath") {
	extendsFrom(configurations.named("runtimeClasspath").get())
}

dependencies {
	minecraft("com.mojang:minecraft:${prop("deps.minecraft")}")
	if (sc.current.parsed < "26") {
		mappings(loom.layered {
			officialMojangMappings()
			if (sc.properties.contains("deps.parchment"))
				parchment("org.parchmentmc.data:parchment-${prop("deps.parchment")}@zip")
		})
		annotationProcessor("net.fabricmc:sponge-mixin:0.17.5+mixin.0.8.7")
	}
	include(implementation(annotationProcessor("io.github.llamalad7:mixinextras-fabric:${libs.versions.mixinextras.get()}")!!)!!)
	modImplementation("net.fabricmc:fabric-loader:${prop("deps.fabric-loader")}")
	modImplementation("net.fabricmc.fabric-api:fabric-api:${prop("deps.fabricApi")}+${prop("deps.minecraft")}")
	modImplementation("com.terraformersmc:modmenu:${prop("deps.modmenu")}")
	"gametestImplementation"(sourceSets["main"].output)
	"modGametestImplementation"(
		fabricApi.module(
			"fabric-gametest-api-v1",
			"${prop("deps.fabricApi")}+${prop("deps.minecraft")}"
		)
	)
}


//tasks.named<Jar>("gametestJar") {
//	into("META-INF/jars") {
//		from(configurations.named("gametestInclude"))
//	}
//}

if (remap) {
	val gametestJar = tasks.named<Jar>("gametestJar") {
		archiveClassifier.set("gametest-unmapped")
	}

	val remapClientGametestJar =
		tasks.register<RemapJarTask>("remapGametestJar") {
			inputFile.set(gametestJar.flatMap { it.archiveFile })

			sourceNamespace.set("named")
			targetNamespace.set("intermediary")

			archiveClassifier.set("gametest")
			classpath.from(sourceSets["gametest"].compileClasspath)
		}

	tasks.named("build") {
		dependsOn(remapClientGametestJar)
	}
}

