package com.odinokland.constantmusic.gametest;

import net.minecraft.gametest.framework.GameTestHelper;
import com.odinokland.constantmusic.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BiConsumer;
import java.util.function.Consumer;


public class GametestConstants {
	public static final String MOD_ID = Constants.MOD_ID + "_gametest";
	public static final String MOD_NAME = "Constant Music Game Tests";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

	public static void commonInit() {

	}

	public static void initTests(BiConsumer<String, Consumer<GameTestHelper>> consumer) {
		consumer.accept("test_mod_loaded_and_world_entered", ConstantMusicGameTests::testModLoadedAndWorldEntered);
		consumer.accept("test_jukebox_music_suppression_and_resumption", ConstantMusicGameTests::testJukeboxMusicSuppressionAndResumption);
		consumer.accept("test_delay_slider_option_and_config_screen", ConstantMusicGameTests::testDelaySliderOptionAndConfigScreen);
	}
}
