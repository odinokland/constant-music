package com.odinokland.constantmusic.gametest;
//? > 27.1 {
/*//? !fabric {
//? if >=1.21.5 {
/^import com.odinokland.constantmusic.ConstantMusic;
import com.odinokland.constantmusic.Constants;
import com.odinokland.constantmusic.util.JukeboxTrackerUtility;
//? if >=1.21.5 {
//import com.mojang.serialization.MapCodec;
//?}
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
//? if >=1.21.5 {
/^import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
^///?}
//? if <1.21.5 {
import net.minecraft.gametest.framework.GameTest;
 //?}
import net.minecraft.gametest.framework.GameTestHelper;
//? if >=1.21.5 {
/^import net.minecraft.gametest.framework.GameTestInstance;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
^///?}
import net.minecraft.network.chat.Component;
//? if >=1.21.5 {
//import net.minecraft.network.chat.MutableComponent;
//?}
//? if >=1.21.11 {
//import net.minecraft.resources.ResourceLocation;
//?} else {
import net.minecraft.resources.ResourceLocation;
//?}
import net.minecraft.world.entity.Entity;
//? if >=26.1 {
//import net.minecraft.world.inventory.ContainerInput;
//?} else {
//?}
import net.minecraft.world.level.block.Blocks;
//? neoforge {
/^//? if >=1.21.5 {
//import net.neoforged.bus.api.SubscribeEvent;
//?}
//? if >=1.21.5 {
//import net.neoforged.fml.common.EventBusSubscriber;
//?} else {
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
//?}
//? if >=1.21.5 {
//import net.neoforged.neoforge.event.RegisterGameTestsEvent;
//?}
^///? } else if forge {
//? if >=1.21.5 {
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//?}
//? if >=1.21.5 {
//import net.minecraftforge.fml.common.Mod;
//?}
//? if >=1.21.5 {
/^import net.minecraftforge.gametest.ForgeGameTestHooks;
import net.minecraftforge.registries.RegisterEvent;
^///?}
//? }

import java.util.List;
//? if >=1.21.5 {
//import java.util.function.Consumer;
//?}

//? neoforge {
//@EventBusSubscriber(modid = Constants.MOD_ID)
//? } else if forge {
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
//? }
public class GameTestRegistration {
	//? if >=1.21.11 {
	/^private static final ResourceLocation EMPTY_TEMPLATE =
			ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "gametest/empty");
	^///?} else {
    private static final ResourceLocation EMPTY_TEMPLATE =
            ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "gametest/empty");
    //?}
	private static final List<TestRegistration> TESTS = List.of(
			new TestRegistration("test_mod_loaded_and_world_entered", 20, ConstantMusicGameTests::testModLoadedAndWorldEntered),
			new TestRegistration("test_jukebox_music_suppression_and_resumption", 20, ConstantMusicGameTests::testJukeboxMusicSuppressionAndResumption),
			new TestRegistration("test_delay_slider_option_and_config_screen", 20, ConstantMusicGameTests::testDelaySliderOptionAndConfigScreen)
	);

	//? forge {
	@SubscribeEvent
	public static void registerTestsForge(RegisterEvent event) {
		Constants.LOG.info("Dan: Registration event callback");
		if (!ForgeGameTestHooks.isGametestServer() || !event.getRegistryKey().equals(Registries.TEST_FUNCTION)) {
			return;
		}
		Constants.LOG.info("Dan: Registering gametests");
		ForgeGameTestHooks.gatherTests(ConstantMusicGameTests.class, new ConstantMusicGameTests())
				.forEach((name, ref) -> {
					Constants.LOG.info("Dan: Registering gametest: " + name);
					event.register(Registries.TEST_FUNCTION, name, () ->ref.consumer());
				});
	}
	//? }

	//? neoforge {
	/^@SubscribeEvent
	public static void registerTests(RegisterGameTestsEvent event) {
		if (!isGameTestServerLaunch()) {
			return;
		}

		//? if >=26.1 {
		/^Holder<TestEnvironmentDefinition<?>> environment = event.registerEnvironment(
				ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "default")
		);
		^///?} else if >=1.21.11 {
        /^Holder<TestEnvironmentDefinition> environment = event.registerEnvironment(
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "default")
        );
        ^///?} else {
        Holder<TestEnvironmentDefinition> environment = event.registerEnvironment(
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "default")
        );
        //?}

		for (TestRegistration test : TESTS) {
			registerTest(event, environment, test);
		}
	}

	private static void registerTest(
			RegisterGameTestsEvent event,
			//? if >=26.1 {
			//Holder<TestEnvironmentDefinition<?>> environment,
			//?} else {
			Holder<TestEnvironmentDefinition> environment,
			 //?}
			TestRegistration test
	) {
		event.registerTest(
				testId(test.name()),
				new DirectGameTestInstance(
						new TestData<>(environment, EMPTY_TEMPLATE, test.timeoutTicks(), 0, true),
						test.function()
				)
		);
	}

	private static boolean isGameTestServerLaunch() {
		Boolean serverModLoaderResult = invokeBoolean(
				"net.neoforged.neoforge.server.loading.ServerModLoader",
				"isGameTestServer"
		);
		if (serverModLoaderResult != null) {
			return serverModLoaderResult;
		}

		return Boolean.TRUE.equals(invokeBoolean(
				"net.neoforged.neoforge.gametest.GameTestHooks",
				"isGametestServer"
		));
	}

	private static Boolean invokeBoolean(String className, String methodName) {
		try {
			Object result = Class.forName(className, false, GameTestRegistration.class.getClassLoader())
					.getMethod(methodName)
					.invoke(null);
			return result instanceof Boolean value ? value : null;
		} catch (ReflectiveOperationException | LinkageError ignored) {
			return null;
		}
	}

	^///? }

	//? if >=1.21.11 {
	/^private static ResourceLocation testId(String name) {
		return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name);
	}
	^///?} else {
    private static ResourceLocation testId(String name) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name);
    }
    //?}

	private record TestRegistration(String name, int timeoutTicks, Consumer<GameTestHelper> function) {
	}

	private static final class DirectGameTestInstance extends GameTestInstance {
		private final Consumer<GameTestHelper> function;

		//? if >=26.1 {
		//private DirectGameTestInstance(TestData<Holder<TestEnvironmentDefinition<?>>> testData, Consumer<GameTestHelper> function) {
			//?} else {
			private DirectGameTestInstance(TestData<Holder<TestEnvironmentDefinition>> testData, Consumer<GameTestHelper> function) {
			 //?}
			super(testData);
			this.function = function;
		}

		@Override
		public void run(GameTestHelper helper) {
			function.accept(helper);
		}

		@Override
		public MapCodec<? extends GameTestInstance> codec() {
			return FunctionGameTestInstance.CODEC;
		}

		@Override
		protected MutableComponent typeDescription() {
			return Component.literal("direct");
		}
	}
}
^///? }
//? }
*///? }
