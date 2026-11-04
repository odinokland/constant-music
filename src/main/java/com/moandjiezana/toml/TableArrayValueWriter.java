package com.moandjiezana.toml;

import java.util.Collection;

import static com.moandjiezana.toml.ValueWriters.WRITERS;

/**
 * The type Table array value writer.
 */
class TableArrayValueWriter extends ArrayValueWriter {
	/**
	 * The Table array value writer.
	 */
	static final ValueWriter TABLE_ARRAY_VALUE_WRITER = new TableArrayValueWriter();

	@Override
	public boolean canWrite(Object value) {
		return isArrayish(value) && !isArrayOfPrimitive(value);
	}

	@Override
	public void write(Object from, WriterContext context) {
		Collection<?> values = normalize(from);

		WriterContext subContext = context.pushTableFromArray();

		for (Object value : values) {
			WRITERS.findWriterFor(value).write(value, subContext);
		}
	}

	private TableArrayValueWriter() {
	}

	@Override
	public String toString() {
		return "table-array";
	}
}
