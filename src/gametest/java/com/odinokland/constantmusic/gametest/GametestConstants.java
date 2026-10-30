package com.odinokland.constantmusic.gametest;

import com.odinokland.constantmusic.gametest.shared.ConstantMusicGameTests;
import net.minecraft.gametest.framework.GameTestHelper;
//? forge && >= 1.2.5 {
import net.minecraftforge.registries.DeferredRegister;
//? } else if neoforge && >= 1.2.5 {
//import net.neoforged.neoforge.registries.DeferredRegister;
//? }
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;


public class GametestConstants {
	public static final String MOD_ID = /*$ mod_gametest_id */"constantmusic_gametest";
	public static final String MAIN_MOD_ID = /*$ mod_id */"constantmusic";
	public static final String MOD_NAME = "Constant Music Game Tests";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
	public static final Map<String, Consumer<GameTestHelper>> TESTS = Map.of(
			"test_mod_loaded_and_world_entered", ConstantMusicGameTests::testModLoadedAndWorldEntered,
			"test_jukebox_music_suppression_and_resumption", ConstantMusicGameTests::testJukeboxMusicSuppressionAndResumption,
			"test_delay_slider_option_and_config_screen", ConstantMusicGameTests::testDelaySliderOptionAndConfigScreen
	);
	public static void commonInit() {

	}

	//? >= 1.21.5 && !fabric {
	/*public static void initializeTest(DeferredRegister<Consumer<GameTestHelper>> testFunctionRegistry) {
		TESTS.forEach((key, value) -> testFunctionRegistry.register(key, () ->value));
	}
	*///? }
}
