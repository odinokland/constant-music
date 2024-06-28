package com.moandjiezana.toml;

import java.util.concurrent.atomic.AtomicInteger;

import static com.moandjiezana.toml.IdentifierConverter.IDENTIFIER_CONVERTER;

class TomlParser {

  static com.moandjiezana.toml.Results run(String tomlString) {
    final com.moandjiezana.toml.Results results = new com.moandjiezana.toml.Results();
    
    if (tomlString.isEmpty()) {
      return results;
    }
    
    AtomicInteger index = new AtomicInteger();
    boolean inComment = false;
    AtomicInteger line = new AtomicInteger(1);
    com.moandjiezana.toml.Identifier identifier = null;
    Object value = null;
    
    for (int i = index.get(); i < tomlString.length(); i = index.incrementAndGet()) {
      char c = tomlString.charAt(i);
      
      if (results.errors.hasErrors()) {
        break;
      }

      if (c == '#' && !inComment) {
        inComment = true;
      } else if (!Character.isWhitespace(c) && !inComment && identifier == null) {
        com.moandjiezana.toml.Identifier id = IDENTIFIER_CONVERTER.convert(tomlString, index, new com.moandjiezana.toml.Context(null, line, results.errors));
        
        if (id != com.moandjiezana.toml.Identifier.INVALID) {
          if (id.isKey()) {
            identifier = id;
          } else if (id.isTable()) {
            results.startTables(id, line);
          } else if (id.isTableArray()) {
            results.startTableArray(id, line);
          }
        }
      } else if (c == '\n') {
        inComment = false;
        identifier = null;
        value = null;
        line.incrementAndGet();
      } else if (!inComment && identifier != null && identifier.isKey() && value == null && !Character.isWhitespace(c)) {
        value = com.moandjiezana.toml.ValueReaders.VALUE_READERS.convert(tomlString, index, new com.moandjiezana.toml.Context(identifier, line, results.errors));
        
        if (value instanceof com.moandjiezana.toml.Results.Errors) {
          results.errors.add((com.moandjiezana.toml.Results.Errors) value);
        } else {
          results.addValue(identifier.getName(), value, line);
        }
      } else if (value != null && !inComment && !Character.isWhitespace(c)) {
        results.errors.invalidTextAfterIdentifier(identifier, c, line.get());
      }
    }

    return results;
  }
  
  private TomlParser() {}
}
