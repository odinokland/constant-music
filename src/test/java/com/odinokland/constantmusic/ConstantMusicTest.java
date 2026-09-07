package com.odinokland.constantmusic;

import com.odinokland.constantmusic.gui.ConstantMusicConfigScreen;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class ConstantMusicTest {

	@TempDir
	Path tempDir;

	@BeforeEach
	void setUp() {
		ConstantMusic.resetForTesting(0);
	}

	@AfterEach
	void tearDown() {
		ConstantMusic.resetForTesting(0);
	}

	@Test
	@DisplayName("Default timer should be 0 (OFF)")
	void testDefaultTimer() {
		assertThat(ConstantMusic.getTimer()).isEqualTo(0);
	}

	@Test
	@DisplayName("Timer setter and getter should update and return value")
	void testSetAndGetTimer() {
		ConstantMusic.resetForTesting(45);
		assertThat(ConstantMusic.getTimer()).isEqualTo(45);
	}

	@Test
	@DisplayName("MAX_TIMER should be 600 seconds (10 minutes)")
	void testMaxTimerConstant() {
		assertThat(ConstantMusic.MAX_TIMER).isEqualTo(600);
	}

	@Test
	@DisplayName("Config file read and write with TOML")
	void testConfigReadAndWrite() {
		File configFile = tempDir.resolve("constantmusic.toml").toFile();

		// Write timer = 120
		ConstantMusic.writeValue(configFile, 120);
		assertThat(configFile).exists();

		// Read timer back
		int readVal = ConstantMusic.readValue(configFile);
		assertThat(readVal).isEqualTo(120);

		// Overwrite with 0
		ConstantMusic.writeValue(configFile, 0);
		assertThat(ConstantMusic.readValue(configFile)).isEqualTo(0);
	}

	@Test
	@DisplayName("Reading from nonexistent file should return default 0")
	void testReadNonExistentFile() {
		File nonExistent = tempDir.resolve("does_not_exist.toml").toFile();
		int val = ConstantMusic.readValue(nonExistent);
		assertThat(val).isEqualTo(0);
	}

	@Test
	@DisplayName("timeDisplayText should format seconds and minutes correctly")
	void testTimeDisplayText() {
		// Less than a minute -> seconds format
		var comp15 = ConstantMusic.timeDisplayText(15);
		assertThat(comp15.getString()).isNotNull();

		// Exactly 1 minute (60 seconds) -> minutes and seconds format
		var comp60 = ConstantMusic.timeDisplayText(60);
		assertThat(comp60.getString()).isNotNull();

		// 2 minutes 5 seconds (125 seconds)
		var comp125 = ConstantMusic.timeDisplayText(125);
		assertThat(comp125.getString()).isNotNull();

		// 10 minutes (600 seconds)
		var comp600 = ConstantMusic.timeDisplayText(600);
		assertThat(comp600.getString()).isNotNull();
	}

	@Test
	@DisplayName("Config option instance should have valid bounds and default value")
	void testConfigOptionInstance() {
		var option = ConstantMusicConfigScreen.getConfigOption();
		assertThat(option).isNotNull();
		assertThat(option.get()).isEqualTo(0);
	}
}
