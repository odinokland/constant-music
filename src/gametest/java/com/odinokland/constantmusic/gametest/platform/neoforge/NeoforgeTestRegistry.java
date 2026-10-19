package com.odinokland.constantmusic.gametest.platform.neoforge;

//? >= 1.21.5 && neoforge {
/*import com.odinokland.constantmusic.gametest.shared.ConstantMusicGameTests;
import com.odinokland.constantmusic.gametest.GametestConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.function.Consumer;

@EventBusSubscriber(modid = GametestConstants.MOD_ID)
public class NeoforgeTestRegistry {
	private static Holder<TestEnvironmentDefinition> environment;
	@SubscribeEvent
	public static void registerFunctions(RegisterEvent event) {
		event.register(Registries.TEST_FUNCTION, helper -> {
			registerFunction(helper, "test_mod_loaded_and_world_entered", ConstantMusicGameTests::testModLoadedAndWorldEntered);
			registerFunction(helper, "test_jukebox_music_suppression_and_resumption", ConstantMusicGameTests::testJukeboxMusicSuppressionAndResumption);
			registerFunction(helper, "test_delay_slider_option_and_config_screen", ConstantMusicGameTests::testDelaySliderOptionAndConfigScreen);
		});
	}

	private static void registerFunction(RegisterEvent.RegisterHelper<Consumer<GameTestHelper>> helper, String name, Consumer<GameTestHelper> function) {
		helper.register(ResourceKey.create(Registries.TEST_FUNCTION, ResourceLocation.fromNamespaceAndPath(GametestConstants.MOD_ID, name)), function);
	}

	@SubscribeEvent
	public static void registerTests(RegisterGameTestsEvent event) {
		if (environment == null) {
			environment = event.registerEnvironment(
					ResourceLocation.fromNamespaceAndPath(GametestConstants.MOD_ID, "test_env"),
					new TestEnvironmentDefinition.AllOf());
		}

		var structure = ResourceLocation.withDefaultNamespace("empty");

//		register(event, "test_mod_loaded_and_world_entered", environment, structure, 200);
//		register(event, "test_jukebox_music_suppression_and_resumption", environment, structure, 200);
//		register(event, "test_delay_slider_option_and_config_screen", environment, structure, 200);
	}

	private static void register(RegisterGameTestsEvent event, String name,
								 Holder<TestEnvironmentDefinition> env,
								 ResourceLocation structure, int maxTicks) {
		var data = new TestData<>(env, structure, maxTicks, 0, true);
		var functionKey = ResourceKey.create(Registries.TEST_FUNCTION, ResourceLocation.fromNamespaceAndPath(GametestConstants.MOD_ID, name));
		event.registerTest(ResourceLocation.fromNamespaceAndPath(GametestConstants.MOD_ID, name),
				new FunctionGameTestInstance(functionKey, data));
	}

}
*///? }
