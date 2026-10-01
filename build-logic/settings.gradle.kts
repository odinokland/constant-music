import java.util.Properties
rootProject.name = "build-logic"

val rootProperties = Properties().apply {
	val rootPropsFile = file("../gradle.properties") // Adjust path up to the main root project directory
	if (rootPropsFile.exists()) {
		rootPropsFile.inputStream().use { load(it) }
	}
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
			val stonecutterVersion = rootProperties.getProperty("stonecutter.version")
			val loomxVersion = rootProperties.getProperty("loomx.version")
			val foojayVersion = rootProperties.getProperty("foojay.version")
            from(files("../gradle/libs.versions.toml"))
			version("stonecutter", stonecutterVersion)
			version("loomx", loomxVersion)
			version("foojay", foojayVersion)
        }
    }
}

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}
