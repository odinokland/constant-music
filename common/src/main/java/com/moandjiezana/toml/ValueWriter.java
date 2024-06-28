package com.moandjiezana.toml;

interface ValueWriter {
  boolean canWrite(Object value);

  void write(Object value, com.moandjiezana.toml.WriterContext context);

  boolean isPrimitiveType();
}
