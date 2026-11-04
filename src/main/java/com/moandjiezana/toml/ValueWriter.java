package com.moandjiezana.toml;

/**
 * The interface Value writer.
 */
interface ValueWriter {
	/**
	 * Can write boolean.
	 *
	 * @param value the value
	 * @return the boolean
	 */
	boolean canWrite(Object value);

	/**
	 * Write.
	 *
	 * @param value   the value
	 * @param context the context
	 */
	void write(Object value, WriterContext context);

	/**
	 * Is primitive type boolean.
	 *
	 * @return the boolean
	 */
	boolean isPrimitiveType();
}
