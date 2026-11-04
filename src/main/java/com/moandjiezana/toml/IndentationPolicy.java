package com.moandjiezana.toml;

/**
 * Controls how a {@link TomlWriter} indents tables and key/value pairs.
 * <p>
 * The default policy is to not indent.
 */
class IndentationPolicy {
	private final int tableIndent;
	private final int keyValueIndent;
	private final int arrayDelimiterPadding;

	/**
	 * Instantiates a new Indentation policy.
	 *
	 * @param keyIndentation        the key indentation
	 * @param tableIndentation      the table indentation
	 * @param arrayDelimiterPadding the array delimiter padding
	 */
	IndentationPolicy(int keyIndentation, int tableIndentation, int arrayDelimiterPadding) {
		this.keyValueIndent = keyIndentation;
		this.tableIndent = tableIndentation;
		this.arrayDelimiterPadding = arrayDelimiterPadding;
	}

	/**
	 * Gets table indent.
	 *
	 * @return the table indent
	 */
	int getTableIndent() {
		return tableIndent;
	}

	/**
	 * Gets key value indent.
	 *
	 * @return the key value indent
	 */
	int getKeyValueIndent() {
		return keyValueIndent;
	}

	/**
	 * Gets array delimiter padding.
	 *
	 * @return the array delimiter padding
	 */
	int getArrayDelimiterPadding() {
		return arrayDelimiterPadding;
	}
}
