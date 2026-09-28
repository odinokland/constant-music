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
import java.util.Properties

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
	val awDir = rootProject.layout.projectDirectory.dir("src/main/resources/aw/").asFile
	val resolvedFile = findResolvedAccessFile(targetVersion, awDir, type)
	return resolvedFile ?: defaultFile
}

fun findResolvedAccessFile(
	targetVersion: AnyVersion,
	awDir: File,
	type: AccessType
): File? {
	val safeVersionQuery = Regex.escape(targetVersion)
	val resolvedFile = awDir.listFiles()?.firstOrNull { file ->
		if (!file.isFile || !file.name.endsWith(".${type.keyword}")) return@firstOrNull false
		val fileVersionPart = file.name.removeSuffix(".${type.keyword}")
		val fileMatchesQueryPattern = Regex("""\b$safeVersionQuery(\.\d+)*(-[0-9A-Za-z.-]+)?\b""")
		val safeFileQuery = Regex.escape(fileVersionPart)
		val queryMatchesFilePattern = Regex("""\b$safeFileQuery(\.\d+)*(-[0-9A-Za-z.-]+)?\b""")
		fileMatchesQueryPattern.containsMatchIn(fileVersionPart) ||
			queryMatchesFilePattern.containsMatchIn(targetVersion)
	}
	return resolvedFile
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
