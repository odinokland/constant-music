package com.odinokland.constantmusic.test;

//import com.odinokland.constantmusic.gui.ConstantMusicConfigScreen;
import com.odinokland.constantmusic.core.ConstantMusicManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class ConstantMusicManagerTest {
	@TempDir
	static Path tempDir;

	@BeforeAll
	static void init() {
		ConstantMusicManager.SINGLETON.init(tempDir.resolve("constantmusic.toml").toFile());
	}

	@BeforeEach
	void setUp() {
		ConstantMusicManager.SINGLETON.resetForTesting(0);
	}

	@AfterEach
	void tearDown() {
		ConstantMusicManager.SINGLETON.resetForTesting(0);
	}

	@Test
	@DisplayName("Default timer should be 0 (OFF)")
	void testDefaultTimer() {
		assertThat(ConstantMusicManager.SINGLETON.getTimer()).isEqualTo(0);
	}

	@Test
	@DisplayName("Timer setter and getter should update and return value")
	void testSetAndGetTimer() {
		ConstantMusicManager.SINGLETON.resetForTesting(45);
		assertThat(ConstantMusicManager.SINGLETON.getTimer()).isEqualTo(45);
	}

	@Test
	@DisplayName("MAX_TIMER should be 600 seconds (10 minutes)")
	void testMaxTimerConstant() {
		assertThat(ConstantMusicManager.MAX_TIMER).isEqualTo(600);
	}

	@Test
	@DisplayName("timeDisplayText should format seconds and minutes correctly")
	void testTimeDisplayText() {
		// Less than a minute -> seconds format
		var comp15 = ConstantMusicManager.timeDisplayText(15);
		assertThat(comp15.getString()).isNotNull();

		// Exactly 1 minute (60 seconds) -> minutes and seconds format
		var comp60 = ConstantMusicManager.timeDisplayText(60);
		assertThat(comp60.getString()).isNotNull();

		// 2 minutes 5 seconds (125 seconds)
		var comp125 = ConstantMusicManager.timeDisplayText(125);
		assertThat(comp125.getString()).isNotNull();

		// 10 minutes (600 seconds)
		var comp600 = ConstantMusicManager.timeDisplayText(600);
		assertThat(comp600.getString()).isNotNull();
	}
}
