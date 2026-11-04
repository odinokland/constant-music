package com.odinokland.constantmusic.test.core;

import com.odinokland.constantmusic.core.ConfigManager;
import com.odinokland.constantmusic.core.ConstantMusicManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigManagerTest {
	@TempDir
	Path tempDir;

	@Test
	@DisplayName("Config file read and write with TOML")
	void testConfigReadAndWrite() {
		File configFile = tempDir.resolve("constantmusic.toml").toFile();

		ConfigManager manager = new ConfigManager(configFile);
		manager.load();

		// Write timer = 120
		manager.getData().timer = 120;
		manager.save();
		assertThat(configFile).exists();

		manager = new ConfigManager(configFile);
		manager.load();
		// Read timer back
		int readVal = manager.getData().timer;
		assertThat(readVal).isEqualTo(120);

		// Overwrite with 0
		manager.getData().timer = 0;
		manager.save();

		manager = new ConfigManager(configFile);
		manager.load();
		assertThat(manager.getData().timer).isEqualTo(0);
	}

	@Test
	@DisplayName("Reading from nonexistent file should return default 0")
	void testReadNonExistentFile() {
		File nonExistent = tempDir.resolve("does_not_exist.toml").toFile();
		ConfigManager manager = new ConfigManager(nonExistent);
		manager.load();
		assertThat(manager.getData().timer).isEqualTo(0);
	}
}
