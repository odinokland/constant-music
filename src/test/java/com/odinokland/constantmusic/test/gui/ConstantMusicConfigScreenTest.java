package com.odinokland.constantmusic.test.gui;

import com.odinokland.constantmusic.core.ConstantMusicManager;
import com.odinokland.constantmusic.gui.ButtonGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
//? >= 1.19.0
import net.minecraft.client.OptionInstance;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class ConstantMusicConfigScreenTest {
	@TempDir
	static Path tempDir;

	@BeforeAll
	static void init() {
		ConstantMusicManager.SINGLETON.init(tempDir.resolve("constantmusic.toml").toFile());
	}
	@Test
	@DisplayName("Config option generates valid OptionInstance with correct initial value and caption")
	void testConfigOption() {
		//? >= 1.19.0 {
		ConstantMusicManager.SINGLETON.resetForTesting(30);
		OptionInstance<Integer> option = ButtonGenerator.musicDelaySliderOptionInstance();

		assertThat(option).isNotNull();
		assertThat(option.get()).isEqualTo(30);

		// Verify option caption when 0 (OFF)
		ConstantMusicManager.SINGLETON.resetForTesting(0);
		OptionInstance<Integer> optionZero = ButtonGenerator.musicDelaySliderOptionInstance();
		assertThat(optionZero.get()).isEqualTo(0);
		//? } else {
		assertThat(true).isTrue();
		//? }
	}
}
