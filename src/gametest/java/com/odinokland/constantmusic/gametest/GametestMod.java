package com.odinokland.constantmusic.gametest;

//? forge || neoforge {
//? < 1.21.5 {
import com.odinokland.constantmusic.Constants;
//import com.odinokland.constantmusic.gametest.platform.GameTests;
//? forge {
import net.minecraftforge.event.RegisterGameTestsEvent;
//? }
//? }
//? forge {
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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
//import net.neoforged.bus.api.IEventBus;
//? }
*///? }

@Mod(GametestConstants.MOD_ID)
public class GametestMod {
	//? < 1.21.5 {
	public GametestMod() {

	}

	//? }
	//? >= 1.21.5 {
	/*//? forge {
	public static final DeferredRegister<Consumer<GameTestHelper>> GAMETESTS = DeferredRegister.create(Registries.TEST_FUNCTION, GametestConstants.MOD_ID);
	private static final List<String> testKeys = new ArrayList<>();

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
		GametestConstants.LOGGER.info("Registering Gametest: " + name);
		testKeys.add(name);
		GAMETESTS.register(name, () -> consumer);
		storeKeys();
	}

	private static void storeKeys() {
		System.setProperty("McTestRunnerTests", String.join(",", testKeys.stream().map(name -> GametestConstants.MOD_ID + ":" + name).toArray(String[]::new)));
	}

	public static void gatherData(final GatherDataEvent event) {
		GametestConstants.LOGGER.info("Gathering Gametest Data");
		event.getGenerator().addProvider(true, (DataProvider.Factory<GametestInstanceProvider>) output -> new GametestInstanceProvider(output, testKeys));
	}
	//? }
	//? neoforge {
	/^public GametestMod(IEventBus eventBus) {

	}
	^///? }
	*///? }
}
//? }
