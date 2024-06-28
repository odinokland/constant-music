package com.moandjiezana.toml;

import java.util.concurrent.atomic.AtomicInteger;

class Context {
  final com.moandjiezana.toml.Identifier identifier;
  final AtomicInteger line;
  final com.moandjiezana.toml.Results.Errors errors;
  
  public Context(com.moandjiezana.toml.Identifier identifier, AtomicInteger line, com.moandjiezana.toml.Results.Errors errors) {
    this.identifier = identifier;
    this.line = line;
    this.errors = errors;
  }

  public Context with(com.moandjiezana.toml.Identifier identifier) {
    return new Context(identifier, line, errors);
  }
}
