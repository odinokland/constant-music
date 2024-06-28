package com.moandjiezana.toml;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

class Results {
  
  static class Errors {
    
    private final StringBuilder sb = new StringBuilder();
    
    void duplicateTable(String table, int line) {
      sb.append("Duplicate table definition on line ")
        .append(line)
        .append(": [")
        .append(table)
        .append("]");
    }

    public void tableDuplicatesKey(String table, AtomicInteger line) {
      sb.append("Key already exists for table defined on line ")
        .append(line.get())
        .append(": [")
        .append(table)
        .append("]");
    }

    public void keyDuplicatesTable(String key, AtomicInteger line) {
      sb.append("Table already exists for key defined on line ")
        .append(line.get())
        .append(": ")
        .append(key);
    }
    
    void emptyImplicitTable(String table, int line) {
      sb.append("Invalid table definition due to empty implicit table name: ")
        .append(table);
    }
    
    void invalidTable(String table, int line) {
      sb.append("Invalid table definition on line ")
        .append(line)
        .append(": ")
        .append(table)
        .append("]");
    }
    
    void duplicateKey(String key, int line) {
      sb.append("Duplicate key");
      if (line > -1) {
        sb.append(" on line ")
          .append(line);
      }
      sb.append(": ")
        .append(key);
    }
    
    void invalidTextAfterIdentifier(com.moandjiezana.toml.Identifier identifier, char text, int line) {
      sb.append("Invalid text after key ")
        .append(identifier.getName())
        .append(" on line ")
        .append(line)
        .append(". Make sure to terminate the value or add a comment (#).");
    }
    
    void invalidKey(String key, int line) {
      sb.append("Invalid key on line ")
        .append(line)
        .append(": ")
        .append(key);
    }
    
    void invalidTableArray(String tableArray, int line) {
      sb.append("Invalid table array definition on line ")
        .append(line)
        .append(": ")
        .append(tableArray);
    }
    
    void invalidValue(String key, String value, int line) {
      sb.append("Invalid value on line ")
        .append(line)
        .append(": ")
        .append(key)
        .append(" = ")
        .append(value);
    }
    
    void unterminatedKey(String key, int line) {
      sb.append("Key is not followed by an equals sign on line ")
        .append(line)
        .append(": ")
        .append(key);
    }
    
    void unterminated(String key, String value, int line) {
      sb.append("Unterminated value on line ")
        .append(line)
        .append(": ")
        .append(key)
        .append(" = ")
        .append(value.trim());
    }

    public void heterogenous(String key, int line) {
      sb.append(key)
        .append(" becomes a heterogeneous array on line ")
        .append(line);
    }
    
    boolean hasErrors() {
      return sb.length() > 0;
    }
    
    @Override
    public String toString() {
      return sb.toString();
    }

    public void add(Errors other) {
      sb.append(other.sb);
    }
  }
  
  final Errors errors = new Errors();
  private final Set<String> tables = new HashSet<String>();
  private final Deque<com.moandjiezana.toml.Container> stack = new ArrayDeque<com.moandjiezana.toml.Container>();

  Results() {
    stack.push(new com.moandjiezana.toml.Container.Table(""));
  }

  void addValue(String key, Object value, AtomicInteger line) {
    com.moandjiezana.toml.Container currentTable = stack.peek();
    
    if (value instanceof Map) {
      String path = getInlineTablePath(key);
      if (path == null) {
        startTable(key, line);
      } else if (path.isEmpty()) {
        startTables(com.moandjiezana.toml.Identifier.from(key, null), line);
      } else {
        startTables(com.moandjiezana.toml.Identifier.from(path, null), line);
      }
      @SuppressWarnings("unchecked")
      Map<String, Object> valueMap = (Map<String, Object>) value;
      for (Map.Entry<String, Object> entry : valueMap.entrySet()) {
        addValue(entry.getKey(), entry.getValue(), line);
      }
      stack.pop();
    } else if (currentTable.accepts(key)) {
      currentTable.put(key, value);
    } else {
      if (currentTable.get(key) instanceof com.moandjiezana.toml.Container) {
        errors.keyDuplicatesTable(key, line);
      } else {
        errors.duplicateKey(key, line != null ? line.get() : -1);
      }
    }
  }

  void startTableArray(com.moandjiezana.toml.Identifier identifier, AtomicInteger line) {
    String tableName = identifier.getBareName();
    while (stack.size() > 1) {
      stack.pop();
    }

    Keys.Key[] tableParts = Keys.split(tableName);
    for (int i = 0; i < tableParts.length; i++) {
      String tablePart = tableParts[i].name;
      com.moandjiezana.toml.Container currentContainer = stack.peek();

      if (currentContainer.get(tablePart) instanceof com.moandjiezana.toml.Container.TableArray) {
        com.moandjiezana.toml.Container.TableArray currentTableArray = (com.moandjiezana.toml.Container.TableArray) currentContainer.get(tablePart);
        stack.push(currentTableArray);

        if (i == tableParts.length - 1) {
          currentTableArray.put(tablePart, new com.moandjiezana.toml.Container.Table());
        }

        stack.push(currentTableArray.getCurrent());
        currentContainer = stack.peek();
      } else if (currentContainer.get(tablePart) instanceof com.moandjiezana.toml.Container.Table && i < tableParts.length - 1) {
        com.moandjiezana.toml.Container nextTable = (com.moandjiezana.toml.Container) currentContainer.get(tablePart);
        stack.push(nextTable);
      } else if (currentContainer.accepts(tablePart)) {
        com.moandjiezana.toml.Container newContainer = i == tableParts.length - 1 ? new com.moandjiezana.toml.Container.TableArray() : new com.moandjiezana.toml.Container.Table();
        addValue(tablePart, newContainer, line);
        stack.push(newContainer);

        if (newContainer instanceof com.moandjiezana.toml.Container.TableArray) {
          stack.push(((com.moandjiezana.toml.Container.TableArray) newContainer).getCurrent());
        }
      } else {
        errors.duplicateTable(tableName, line.get());
        break;
      }
    }
  }

  void startTables(com.moandjiezana.toml.Identifier id, AtomicInteger line) {
    String tableName = id.getBareName();
    
    while (stack.size() > 1) {
      stack.pop();
    }

    Keys.Key[] tableParts = Keys.split(tableName);
    for (int i = 0; i < tableParts.length; i++) {
      String tablePart = tableParts[i].name;
      com.moandjiezana.toml.Container currentContainer = stack.peek();
      if (currentContainer.get(tablePart) instanceof com.moandjiezana.toml.Container) {
        com.moandjiezana.toml.Container nextTable = (com.moandjiezana.toml.Container) currentContainer.get(tablePart);
        if (i == tableParts.length - 1 && !nextTable.isImplicit()) {
          errors.duplicateTable(tableName, line.get());
          return;
        }
        stack.push(nextTable);
        if (stack.peek() instanceof com.moandjiezana.toml.Container.TableArray) {
          stack.push(((com.moandjiezana.toml.Container.TableArray) stack.peek()).getCurrent());
        }
      } else if (currentContainer.accepts(tablePart)) {
        startTable(tablePart, i < tableParts.length - 1, line);
      } else {
        errors.tableDuplicatesKey(tablePart, line);
        break;
      }
    }
  }

  /**
   * Warning: After this method has been called, this instance is no longer usable.
   */
  Map<String, Object> consume() {
    com.moandjiezana.toml.Container values = stack.getLast();
    stack.clear();

    return ((com.moandjiezana.toml.Container.Table) values).consume();
  }

  private com.moandjiezana.toml.Container startTable(String tableName, AtomicInteger line) {
    com.moandjiezana.toml.Container newTable = new com.moandjiezana.toml.Container.Table(tableName);
    addValue(tableName, newTable, line);
    stack.push(newTable);

    return newTable;
  }

  private com.moandjiezana.toml.Container startTable(String tableName, boolean implicit, AtomicInteger line) {
    com.moandjiezana.toml.Container newTable = new com.moandjiezana.toml.Container.Table(tableName, implicit);
    addValue(tableName, newTable, line);
    stack.push(newTable);

    return newTable;
  }
  
  private String getInlineTablePath(String key) {
    Iterator<com.moandjiezana.toml.Container> descendingIterator = stack.descendingIterator();
    StringBuilder sb = new StringBuilder();
    
    while (descendingIterator.hasNext()) {
      com.moandjiezana.toml.Container next = descendingIterator.next();
      if (next instanceof com.moandjiezana.toml.Container.TableArray) {
        return null;
      }
      
      com.moandjiezana.toml.Container.Table table = (com.moandjiezana.toml.Container.Table) next;
      
      if (table.name == null) {
        break;
      }
      
      if (sb.length() > 0) {
        sb.append('.');
      }
      
      sb.append(table.name);
    }
    
    if (sb.length() > 0) {
      sb.append('.');
    }

    sb.append(key)
      .insert(0, '[')
      .append(']');
    
    return sb.toString();
  }
}