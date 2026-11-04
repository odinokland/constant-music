package com.odinokland.constantmusic.core;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import com.odinokland.constantmusic.util.Constants;

import java.io.File;
import java.io.IOException;

/**
 * The type Config manager.
 */
public class ConfigManager {
	private final File file;
	private final TomlWriter writer;
	private ConfigData data;

	/**
	 * Instantiates a new Config manager.
	 *
	 * @param file the file
	 */
	public ConfigManager(File file) {
		Constants.LOG.info("Creating ConfigManager with file: {}", file.getAbsolutePath());
		this.file = file;
		this.writer = new TomlWriter();
		this.data = new ConfigData(); // Default values
	}

	/**
	 * Load.
	 */
	public void load() {
		if (!file.exists()) {
			save();
			return;
		}

		try {
			this.data = new Toml().read(file).to(ConfigData.class);
		} catch (Exception e) {
			System.err.println("Failed to read TOML data: " + e.getMessage());
			this.data = new ConfigData();
		}
	}

	/**
	 * Save.
	 */
	public void save() {
		try {
			// Ensure parent directories exist
			if (file.getParentFile() != null) {
				file.getParentFile().mkdirs();
			}
			writer.write(this.data, file);
		} catch (IOException e) {
			System.err.println("Failed to write TOML data: " + e.getMessage());
		}
	}

	/**
	 * Gets data.
	 *
	 * @return the data
	 */
	public ConfigData getData() {
		return this.data;
	}
}
