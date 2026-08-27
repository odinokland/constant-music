enum class AccessType(val keyword: String) {
	WIDENER("accesswidener"),
	TRANSFORMER("cfg");

	companion object {
		fun fromString(value: String): AccessType {
			return entries.find { it.keyword == value } ?: throw IllegalArgumentException("Invalid type '$value'. Must be 'aw' or 'at'.")
		}
	}
}
