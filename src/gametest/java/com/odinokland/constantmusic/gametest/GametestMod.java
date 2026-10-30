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
import net.minecraftforge.fml.common.Mod;
//? }
//? neoforge {
//import net.neoforged.fml.common.Mod;
//?}
//? >= 1.21.5 {
/*import net.minecraft.gametest.framework.GameTestHelper;
import java.util.function.Consumer;
import java.util.List;
import net.minecraft.core.registries.Registries;
//? forge {
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import com.odinokland.constantmusic.gametest.datagen.GameTestInstanceProvider;
import net.minecraft.data.DataProvider;
//? >= 1.21.6 {
//import net.minecraftforge.eventbus.api.bus.BusGroup;
//? } else {
import net.minecraftforge.eventbus.api.IEventBus;
//? }
//? }
//? neoforge {
/^import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
^///? }
*///? }

@Mod(GametestConstants.MOD_ID)
public class GametestMod {
	//? < 1.21.5 {
	public GametestMod() {

	}

	//? }
	//? >= 1.21.5 {
	/*public static final DeferredRegister<Consumer<GameTestHelper>> GAME_TESTS = DeferredRegister.create(Registries.TEST_FUNCTION, GametestConstants.MOD_ID);

	//? forge {
	public GametestMod(FMLJavaModLoadingContext context) {
		GametestConstants.LOGGER.info("Loading Gametest Mod");
		//? if >= 1.21.6 {
		/^BusGroup modBusGroup = context.getModBusGroup();
		GatherDataEvent.getBus(modBusGroup).addListener(GametestMod::gatherData);
		^///? } else {
		IEventBus modBusGroup = context.getModEventBus();
		modBusGroup.addListener(GametestMod::gatherData);
		//? }
		GAME_TESTS.register(modBusGroup);
		configureTests();
	}

	public static void gatherData(final GatherDataEvent event) {
		GametestConstants.LOGGER.info("Gathering Gametest Data");
		List<String> testNames = GametestConstants.TESTS.keySet().stream().toList();
		event.getGenerator().addProvider(true, (DataProvider.Factory<GameTestInstanceProvider>) output -> new GameTestInstanceProvider(output, testNames));
	}
	//? }
	//? neoforge {
	/^public GametestMod(IEventBus eventBus) {
		GAME_TESTS.register(eventBus);
		configureTests();
	}
	^///? }
	private void configureTests() {
		GametestConstants.initializeTest(GAME_TESTS);
		System.setProperty("McRuntimeGameTestHolders", GametestConstants.MOD_ID);
	}
	*///? }
}
//? }
