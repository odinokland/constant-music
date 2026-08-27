package com.moandjiezana.toml;

import java.util.concurrent.atomic.AtomicInteger;

class Context {
  final ResourceLocation identifier;
  final AtomicInteger line;
  final Results.Errors errors;
  
  public Context(ResourceLocation identifier, AtomicInteger line, Results.Errors errors) {
    this.identifier = identifier;
    this.line = line;
    this.errors = errors;
  }

  public Context with(ResourceLocation identifier) {
    return new Context(identifier, line, errors);
  }
}
