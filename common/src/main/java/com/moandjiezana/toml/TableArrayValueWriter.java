package com.moandjiezana.toml;

import java.util.Collection;

import static com.moandjiezana.toml.ValueWriters.WRITERS;

class TableArrayValueWriter extends com.moandjiezana.toml.ArrayValueWriter {
  static final com.moandjiezana.toml.ValueWriter TABLE_ARRAY_VALUE_WRITER = new TableArrayValueWriter();

  @Override
  public boolean canWrite(Object value) {
    return isArrayish(value) && !isArrayOfPrimitive(value);
  }

  @Override
  public void write(Object from, com.moandjiezana.toml.WriterContext context) {
    Collection<?> values = normalize(from);

    com.moandjiezana.toml.WriterContext subContext = context.pushTableFromArray();

    for (Object value : values) {
      WRITERS.findWriterFor(value).write(value, subContext);
    }
  }

  private TableArrayValueWriter() {}

  @Override
  public String toString() {
    return "table-array";
  }
}
