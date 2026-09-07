package com.odinokland.constantmusic.gametest;
//? >= 27.1 {
/*//? neoforge {
/^import com.odinokland.constantmusic.Constants;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Consumer;

public class GameTestRegistry {
//	public static final DeferredRegister<Consumer<GameTestHelper>> TEST_FUNCTIONS =
//			DeferredRegister.create(BuiltInRegistries.TEST_FUNCTION, Constants.MOD_ID);
//
//	public static final DeferredHolder<Consumer<GameTestHelper>, Consumer<GameTestHelper>> TEST_MOD_LOADED_AND_WORLD_ENTERED =
//			TEST_FUNCTIONS.register("test_mod_loaded_and_world_entered", () -> NeoForgeGameTestRunner::testModLoadedAndWorldEntered);
//
//	public static final DeferredHolder<Consumer<GameTestHelper>, Consumer<GameTestHelper>> TEST_JUKEBOX_MUSIC_SUPPRESSION_AND_RESUMPTION =
//			TEST_FUNCTIONS.register("test_jukebox_music_suppression_and_resumption", () -> NeoForgeGameTestRunner::testJukeboxMusicSuppressionAndResumption);
//
//	public static final DeferredHolder<Consumer<GameTestHelper>, Consumer<GameTestHelper>> TEST_DELAY_SLIDER_OPTION_AND_CONFIG_SCREEN =
//			TEST_FUNCTIONS.register("test_delay_slider_option_and_config_screen", () -> NeoForgeGameTestRunner::testDelaySliderOptionAndConfigScreen);

	public static void onRegisterGameTests(RegisterGameTestsEvent event) {
		Holder<TestEnvironmentDefinition> environment = event.registerEnvironment(
				ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "default"),
				new TestEnvironmentDefinition.AllOf()
		);
		registerTest(event, environment, "test_mod_loaded_and_world_entered", NeoForgeGameTestRunner::testModLoadedAndWorldEntered);
		registerTest(event, environment, "test_jukebox_music_suppression_and_resumption", NeoForgeGameTestRunner::testJukeboxMusicSuppressionAndResumption);
		registerTest(event, environment, "test_delay_slider_option_and_config_screen", NeoForgeGameTestRunner::testDelaySliderOptionAndConfigScreen);


	}

	private static void registerTest(
			RegisterGameTestsEvent event,
			Holder<TestEnvironmentDefinition> environment,
			String path,
			Consumer<GameTestHelper> test
	) {
		ResourceLocation location = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, path);
		ResourceKey<Consumer<GameTestHelper>> functionKey = ResourceKey.create(BuiltInRegistries.TEST_FUNCTION.key(), location);
		event.registerTest(
				location,
				new FunctionGameTestInstance(
						functionKey,
						new TestData<>(environment, ResourceLocation.withDefaultNamespace( "empty"), 400, 0, true)
				)
		);
	}
}
^///? }
*///? }
