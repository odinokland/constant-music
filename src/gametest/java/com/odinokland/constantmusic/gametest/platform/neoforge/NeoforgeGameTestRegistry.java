package com.odinokland.constantmusic.gametest.platform.neoforge;

//? >= 1.21.5 && neoforge {
/*import com.odinokland.constantmusic.gametest.GametestConstants;
import com.odinokland.constantmusic.gametest.shared.ConstantMusicGameTests;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Consumer;

public class NeoforgeGameTestRegistry {

	public static final DeferredRegister<Consumer<GameTestHelper>> TEST_FUNCTIONS =
			DeferredRegister.create(BuiltInRegistries.TEST_FUNCTION, GametestConstants.MOD_ID);

	public static final DeferredHolder<Consumer<GameTestHelper>, Consumer<GameTestHelper>> TEST_MOD_LOADED_AND_WORLD_ENTERED =
			TEST_FUNCTIONS.register("test_mod_loaded_and_world_entered", () -> ConstantMusicGameTests::testModLoadedAndWorldEntered);

	public static final DeferredHolder<Consumer<GameTestHelper>, Consumer<GameTestHelper>> TEST_JUKEBOX_MUSIC_SUPPRESSION_AND_RESUMPTION =
			TEST_FUNCTIONS.register("test_jukebox_music_suppression_and_resumption", () -> ConstantMusicGameTests::testJukeboxMusicSuppressionAndResumption);

	public static final DeferredHolder<Consumer<GameTestHelper>, Consumer<GameTestHelper>> TEST_DELAY_SLIDER_OPTION_AND_CONFIG_SCREEN =
			TEST_FUNCTIONS.register("test_delay_slider_option_and_config_screen", () -> ConstantMusicGameTests::testDelaySliderOptionAndConfigScreen);

	public static void onRegisterGameTests(RegisterGameTestsEvent event) {
//		var environment = event.registerEnvironment(ResourceLocation.fromNamespaceAndPath(GametestConstants.MOD_ID, "test_environment"));
//		var structure = ResourceLocation.fromNamespaceAndPath(GametestConstants.MOD_ID, "empty");
//
//		registerTest(event, TEST_MOD_LOADED_AND_WORLD_ENTERED, environment, structure, 40, 0);
//		registerTest(event, TEST_JUKEBOX_MUSIC_SUPPRESSION_AND_RESUMPTION, environment, structure, 40, 0);
//		registerTest(event, TEST_DELAY_SLIDER_OPTION_AND_CONFIG_SCREEN, environment, structure, 40, 0);
	}

	private static void registerTest(
			RegisterGameTestsEvent event,
			DeferredHolder<Consumer<GameTestHelper>, Consumer<GameTestHelper>> test,
			Holder<TestEnvironmentDefinition<?>> environment,
			ResourceLocation structure,
			int maxTicks,
			int setupTicks
	) {
		var testData = new TestData<>(environment, structure, maxTicks, setupTicks, true);
		event.registerTest(test.getId(), new FunctionGameTestInstance(test.getKey(), testData));
	}
}
*///? }
