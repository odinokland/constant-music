package com.odinokland.constantmusic.test.gui;

import com.odinokland.constantmusic.core.ConstantMusicManager;
import com.odinokland.constantmusic.core.MusicFrequency;
import com.odinokland.constantmusic.gui.ButtonGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class ButtonGeneratorTest {
	@TempDir
	static Path tempDir;

	@BeforeAll
	static void init() {
		ConstantMusicManager.SINGLETON.init(tempDir.resolve("constantmusic.toml").toFile());
	}

	@BeforeEach
	void setUp() {
		ConstantMusicManager manager = ConstantMusicManager.SINGLETON;
		manager.resetForTesting();
	}

	@Test
	@DisplayName("Check the base music delay slider is generated and has correct default value")
	void testMusicDelaySliderGeneration() {
		var option = ButtonGenerator.musicDelaySlider(0,0,150);
		assertThat(option).isNotNull();
		assertThat(option.getValue()).isEqualTo(0);
	}

	@Test
	@DisplayName("Check the base music frequency button is generated and has correct default value")
	void testMusicFrequencyButtonGeneration() {
		var option = ButtonGenerator.musicFrequencyButton(0,0,150);
		assertThat(option).isNotNull();
		assertThat(option.getValue()).isEqualTo(MusicFrequency.DEFAULT);
	}

	@Test
	@DisplayName("Check the option instance music delay slider is generated and has correct default value")
	void testMusicDelaySliderOptionInstanceGeneration() {
		var option = ButtonGenerator.musicDelaySlider(0,0,150);
		assertThat(option).isNotNull();
		assertThat(option.getValue()).isEqualTo(0);
	}

	@Test
	@DisplayName("Check the option instance music frequency button is generated and has correct default value")
	void testMusicFrequencyOptionInstanceGeneration() {
		var option = ButtonGenerator.musicFrequencyButton(0,0,150);
		assertThat(option).isNotNull();
		assertThat(option.getValue()).isEqualTo(MusicFrequency.DEFAULT);
	}
}
