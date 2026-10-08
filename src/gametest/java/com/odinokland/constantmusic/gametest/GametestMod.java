package com.odinokland.constantmusic.gametest;

//? forge || neoforge {
//? forge {
import net.minecraft.gametest.framework.GameTestRegistry;
import net.minecraftforge.fml.common.Mod;
import com.odinokland.constantmusic.gametest.platform.GametestTestRunner;
//? }
//? neoforge {
//import net.neoforged.fml.common.Mod;
//?}
//? >= 1.21.5 {
/*import com.google.common.collect.Maps;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceKey;
import java.util.function.Consumer;
//? forge {
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import com.odinokland.constantmusic.gametest.provider.GametestInstanceProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataProvider;
//? >= 1.21.6 {
//import net.minecraftforge.eventbus.api.bus.BusGroup;
//? } else {
import net.minecraftforge.eventbus.api.IEventBus;
//? }

import java.util.ArrayList;
import java.util.List;
//? }
//? neoforge {
/^import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Map;
^///? }
*///? }

@Mod(GametestConstants.MOD_ID)
public class GametestMod {
	//? forge {
	public GametestMod() {
		GameTestRegistry.register(GametestTestRunner.class);
	}
	//? }
	//? >= 1.21.5 {
	/*//? forge {
	public static final DeferredRegister<Consumer<GameTestHelper>> GAMETESTS = DeferredRegister.create(Registries.TEST_FUNCTION, GametestConstants.MOD_ID);
	private static final List<String> testKeys = new ArrayList<>();
	//?}
	//? neoforge {
	/^public static final DeferredRegister<Consumer<GameTestHelper>> GAMETEST = DeferredRegister.create(BuiltInRegistries.TEST_FUNCTION, GametestConstants.MOD_ID);
	private static final Map<String, ResourceKey<Consumer<GameTestHelper>>> TEST_FUNCTION_MAP = Maps.newHashMap();
	^///? }
	//? forge {
	public GametestMod(FMLJavaModLoadingContext context) {
		GametestConstants.LOGGER.info("Loading Gametest Mod");
		GametestConstants.initTests(GametestMod::registerTest);
		//? if >= 1.21.6 {
		/^BusGroup modBusGroup = context.getModBusGroup();
		GatherDataEvent.getBus(modBusGroup).addListener(GametestMod::gatherData);
		^///? } else {
		IEventBus modBusGroup = context.getModEventBus();
		modBusGroup.addListener(GametestMod::gatherData);
		//? }

		GAMETESTS.register(modBusGroup);
	}

	private static void registerTest(String name, Consumer<GameTestHelper> consumer) {
		testKeys.add(name);
		GAMETESTS.register(name, () -> consumer);
	}

	public static void gatherData(GatherDataEvent event) {
		event.getGenerator().addProvider(true, (DataProvider.Factory<GametestInstanceProvider>) output -> new GametestInstanceProvider(output, testKeys));
	}
	//? }
	//? neoforge {
	/^public GametestMod(IEventBus eventBus) {
		GAMETEST.register(eventBus);
		GametestConstants.initTests(GametestMod::registerTest);
		eventBus.addListener(GametestMod::registerTests);
	}

	public static void registerTests(RegisterGameTestsEvent event) {
		Holder<TestEnvironmentDefinition<?>> env = event.registerEnvironment(ResourceLocation.fromNamespaceAndPath(GametestConstants.MOD_ID, "default"));

		for (Map.Entry<String, ResourceKey<Consumer<GameTestHelper>>> entry : TEST_FUNCTION_MAP.entrySet()) {
			event.registerTest(
					ResourceLocation.fromNamespaceAndPath(GametestConstants.MOD_ID, entry.getKey()),
					new FunctionGameTestInstance(entry.getValue(),
							new TestData<>(env, ResourceLocation.withDefaultNamespace("empty"), 100, 0, true)));
		}
	}

	public static void registerTest(String testId, Consumer<GameTestHelper> consumer) {
		DeferredHolder<Consumer<GameTestHelper>, Consumer<GameTestHelper>> test = GAMETEST.register(testId, () -> consumer);
		TEST_FUNCTION_MAP.putIfAbsent(testId, test.getKey());
	}
	^///? }
	*///? }
}
//? }
