@file:OptIn(kotlinx.serialization.ExperimentalSerializationApi::class)

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class FabricManifest(
	val schemaVersion: Int = 1,
	val id: String,
	val name: String,
	val version: String,
	val authors: List<String>,
	val contributors: List<String>,
	val contact: Map<String, String>,
	val custom: JsonObject?,
	val description: String,
	@EncodeDefault(EncodeDefault.Mode.NEVER)
	val icon: String? = null,
	val license: String,
	val environment: String = "*",
	@EncodeDefault(EncodeDefault.Mode.NEVER)
	val accessWidener: String? = null,
	val entrypoints: Map<String, List<String>>,
	val mixins: List<String>,
	val depends: Map<String, String> = emptyMap(),
	val recommends: Map<String, String> = emptyMap(),
	val breaks: Map<String, String> = emptyMap(),
	val provides: List<String> = emptyList()
)

@Serializable
data class ForgeManifest(
	val modLoader: String = "javafml",
	val loaderVersion: String = "[2,)",
	val license: String,
	val issueTrackerURL: String,
	val mods: List<ForgeMod>,
	val dependencies: Map<String, List<ForgeDependency>> = emptyMap(),
	val mixins: List<ForgeMixin> = emptyList(),
	val clientSideOnly: Boolean = false
)

@Serializable
data class ForgeMod(
	val modId: String,
	val displayName: String,
	val version: String,
	val displayURL: String,
	val modUrl: String,
	@EncodeDefault(EncodeDefault.Mode.NEVER)
	val logoFile: String? = null,
	val authors: String,
	val logoBlur: Boolean = false,
	val credits: String,
	val description: String,
	@EncodeDefault(EncodeDefault.Mode.NEVER)
	val iconFile: String? = null,
	@EncodeDefault(EncodeDefault.Mode.NEVER)
	val bannerFile: String? = null
)

@Serializable
data class ForgeDependency(
	val modId: String,
	val side: String,
	val versionRange: String,
	val mandatory: Boolean,
	val type: String,
	@EncodeDefault(EncodeDefault.Mode.NEVER)
	val ordering: String? = null
)

@Serializable
data class ForgeMixin(val config: String)

@Serializable
data class ForgeAccessTransformer(val file: String)
