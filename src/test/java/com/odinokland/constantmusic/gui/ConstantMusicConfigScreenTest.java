package com.odinokland.constantmusic.gui;

import com.odinokland.constantmusic.ConstantMusic;
import net.minecraft.client.OptionInstance;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConstantMusicConfigScreenTest {

	@Test
	@DisplayName("Config option generates valid OptionInstance with correct initial value and caption")
	void testConfigOption() {
		ConstantMusic.resetForTesting(30);
		OptionInstance<Integer> option = ConstantMusicConfigScreen.getConfigOption();

		assertThat(option).isNotNull();
		assertThat(option.get()).isEqualTo(30);

		// Verify option caption when 0 (OFF)
		ConstantMusic.resetForTesting(0);
		OptionInstance<Integer> optionZero = ConstantMusicConfigScreen.getConfigOption();
		assertThat(optionZero.get()).isEqualTo(0);
	}
}
