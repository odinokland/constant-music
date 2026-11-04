package com.moandjiezana.toml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Container.
 */
abstract class Container {

	/**
	 * Accepts boolean.
	 *
	 * @param key the key
	 * @return the boolean
	 */
	abstract boolean accepts(String key);

	/**
	 * Put.
	 *
	 * @param key   the key
	 * @param value the value
	 */
	abstract void put(String key, Object value);

	/**
	 * Get object.
	 *
	 * @param key the key
	 * @return the object
	 */
	abstract Object get(String key);

	/**
	 * Is implicit boolean.
	 *
	 * @return the boolean
	 */
	abstract boolean isImplicit();

	/**
	 * The type Table.
	 */
	static class Table extends Container {
		private final Map<String, Object> values = new HashMap<String, Object>();
		/**
		 * The Name.
		 */
		final String name;
		/**
		 * The Implicit.
		 */
		final boolean implicit;

		/**
		 * Instantiates a new Table.
		 */
		Table() {
			this(null, false);
		}

		/**
		 * Instantiates a new Table.
		 *
		 * @param name the name
		 */
		public Table(String name) {
			this(name, false);
		}

		/**
		 * Instantiates a new Table.
		 *
		 * @param tableName the table name
		 * @param implicit  the implicit
		 */
		public Table(String tableName, boolean implicit) {
			this.name = tableName;
			this.implicit = implicit;
		}

		@Override
		boolean accepts(String key) {
			return !values.containsKey(key) || values.get(key) instanceof TableArray;
		}

		@Override
		void put(String key, Object value) {
			values.put(key, value);
		}

		@Override
		Object get(String key) {
			return values.get(key);
		}

		boolean isImplicit() {
			return implicit;
		}

		/**
		 * This modifies the Table's internal data structure, such that it is no longer usable.
		 * <p>
		 * Therefore, this method must only be called when all data has been gathered.
		 *
		 * @return A Map-and-List-based of the TOML data
		 */
		Map<String, Object> consume() {
			for (Map.Entry<String, Object> entry : values.entrySet()) {
				if (entry.getValue() instanceof Table) {
					entry.setValue(((Table) entry.getValue()).consume());
				} else if (entry.getValue() instanceof TableArray) {
					entry.setValue(((TableArray) entry.getValue()).getValues());
				}
			}

			return values;
		}

		@Override
		public String toString() {
			return values.toString();
		}
	}

	/**
	 * The type Table array.
	 */
	static class TableArray extends Container {
		private final List<Table> values = new ArrayList<Table>();

		/**
		 * Instantiates a new Table array.
		 */
		TableArray() {
			values.add(new Table());
		}

		@Override
		boolean accepts(String key) {
			return getCurrent().accepts(key);
		}

		@Override
		void put(String key, Object value) {
			values.add((Table) value);
		}

		@Override
		Object get(String key) {
			throw new UnsupportedOperationException();
		}

		boolean isImplicit() {
			return false;
		}

		/**
		 * Gets values.
		 *
		 * @return the values
		 */
		List<Map<String, Object>> getValues() {
			ArrayList<Map<String, Object>> unwrappedValues = new ArrayList<Map<String, Object>>();
			for (Table table : values) {
				unwrappedValues.add(table.consume());
			}
			return unwrappedValues;
		}

		/**
		 * Gets current.
		 *
		 * @return the current
		 */
		Table getCurrent() {
			return values.get(values.size() - 1);
		}

		@Override
		public String toString() {
			return values.toString();
		}
	}

	private Container() {
	}
}
