import dev.kikugie.stonecutter.AnyVersion
import dev.kikugie.stonecutter.StonecutterExperimentalAPI
import dev.kikugie.stonecutter.build.StonecutterBuildExtension
import kotlinx.serialization.json.Json
import me.modmuss50.mpp.ReleaseType
import net.peanuuutz.tomlkt.Toml
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.internal.extensions.stdlib.toDefaultLowerCase
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.maven
import java.io.File
import java.nio.file.Path
import java.util.Properties
import kotlin.io.path.exists

val Project.sc: StonecutterBuildExtension
	get() = extensions.getByType<StonecutterBuildExtension>()

@OptIn(StonecutterExperimentalAPI::class)
fun Project.prop(name: String): String = (project.sc.properties.getAs<String>(name))

fun Project.propsList(vararg segments: String): List<String> = runCatching {
	sc.properties.raw(*segments).asList().map { it.toString() }
}.getOrElse { error("Missing or malformed '${segments.joinToString(".")}' in stonecutter.properties.toml") }

fun Project.env(variable: String): String? {
	providers.environmentVariable(variable).orNull?.let { return it }
	return rootProject.file(".env").takeIf { it.exists() }?.let { f ->
		Properties().apply { f.inputStream().use(::load) }.getProperty(variable)
	}
}
fun Project.envTrue(variable: String): Boolean = env(variable)?.toDefaultLowerCase() == "true"

fun Project.getAccessFile(type: AccessType): File {
	val modId = sc.properties["mod.id"]
	val defaultFile = rootProject.layout.projectDirectory.file("src/main/resources/aw/$modId.${type.keyword}").asFile

	val targetVersion = sc.current.version
	val awDir = rootProject.layout.projectDirectory.dir("src/main/resources/aw/").asFile.toPath()
	val selectedFile = findVersionedFile(
		awDir,
		modId,
		targetVersion,
		type.keyword
	)
	return selectedFile?.toFile() ?: defaultFile
}

fun findVersionedFile(
	directory: Path,
	baseName: String,
	version: String,
	extension: String = "json",
): Path? {
	var parts = version.split('.').map {
		it.toIntOrNull()
			?: throw IllegalArgumentException("Invalid version: $version")
	}
	if (parts.size == 2) {
		parts += 0
	}

	require(parts.size == 3) {
		"Expected a major.minor.patch version: $version"
	}

	val (major, minor, patch) = parts

	val candidates = buildList {
		// Exact version, then earlier patches.
		for (p in patch downTo 0) {
			add("$baseName-$major.$minor.$p.$extension")
		}

		// Major/minor, major, and default fallbacks.
		add("$baseName-$major.$minor.$extension")
		add("$baseName-$major.$extension")
		add("$baseName.$extension")
	}

	return candidates
		.asSequence()
		.map(directory::resolve)
		.firstOrNull { it.exists() }
}

fun RepositoryHandler.strictMaven(
	url: String, vararg groups: String, configure: MavenArtifactRepository.() -> Unit = {}
) = exclusiveContent {
	forRepository { maven(url) { configure() } }
	filter { groups.forEach(::includeGroup) }
}

fun releaseTypeFromChannelTag(channelTag: String): ReleaseType =
	ReleaseType.of(channelTag.substringAfter('-').substringBefore('.').ifEmpty { "stable" })

val JSON = Json { prettyPrint = true; encodeDefaults = true; explicitNulls = false }
val TOML = Toml { }
