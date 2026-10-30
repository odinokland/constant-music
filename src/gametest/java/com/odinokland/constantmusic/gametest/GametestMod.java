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
/*import com.odinokland.constantmusic.gametest.shared.ConstantMusicGameTests;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.fml.common.Mod;
*///?}
//? >= 1.21.5 {
/*import net.minecraft.gametest.framework.GameTestHelper;
import java.util.function.Consumer;
import java.util.ArrayList;
import java.util.List;
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
//? }
//? neoforge {
/^import net.neoforged.bus.api.IEventBus;
import com.odinokland.constantmusic.gametest.platform.neoforge.NeoforgeGameTestRegistry;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

//import static com.odinokland.constantmusic.gametest.platform.neoforge.NeoforgeGameTestRegistry.TEST_FUNCTIONS;
^///? }
*///? }

@Mod(GametestConstants.MOD_ID)
public class GametestMod {
	//? < 1.21.5 {
	public GametestMod() {

	}

	//? }
	//? >= 1.21.5 {
	/*public static final List<String> testKeys = new ArrayList<>();
	//? forge {
	public static final DeferredRegister<Consumer<GameTestHelper>> GAMETESTS = DeferredRegister.create(Registries.TEST_FUNCTION, GametestConstants.MOD_ID);
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
		System.setProperty("McRuntimeGameTestHolders", GametestConstants.MOD_ID);
	}

	private static void registerTest(String name, Consumer<GameTestHelper> consumer) {
		GametestConstants.LOGGER.info("Registering Gametest: " + name);
		testKeys.add(name);
		GAMETESTS.register(name, () -> consumer);
	}

	public static void gatherData(final GatherDataEvent event) {
		GametestConstants.LOGGER.info("Gathering Gametest Data");
		event.getGenerator().addProvider(true, (DataProvider.Factory<GametestInstanceProvider>) output -> new GametestInstanceProvider(output, testKeys));
	}
	//? }
	//? neoforge {
	/^public static final DeferredRegister<Consumer<GameTestHelper>> TEST_FUNCTIONS =
			DeferredRegister.create(BuiltInRegistries.TEST_FUNCTION, GametestConstants.MOD_ID);

	public static final DeferredHolder<Consumer<GameTestHelper>, Consumer<GameTestHelper>> TEST_MOD_LOADED_AND_WORLD_ENTERED =
			TEST_FUNCTIONS.register("test_mod_loaded_and_world_entered", () -> ConstantMusicGameTests::testModLoadedAndWorldEntered);

	public static final DeferredHolder<Consumer<GameTestHelper>, Consumer<GameTestHelper>> TEST_JUKEBOX_MUSIC_SUPPRESSION_AND_RESUMPTION =
			TEST_FUNCTIONS.register("test_jukebox_music_suppression_and_resumption", () -> ConstantMusicGameTests::testJukeboxMusicSuppressionAndResumption);

	public static final DeferredHolder<Consumer<GameTestHelper>, Consumer<GameTestHelper>> TEST_DELAY_SLIDER_OPTION_AND_CONFIG_SCREEN =
			TEST_FUNCTIONS.register("test_delay_slider_option_and_config_screen", () -> ConstantMusicGameTests::testDelaySliderOptionAndConfigScreen);

	public GametestMod(IEventBus eventBus) {
		TEST_FUNCTIONS.register(eventBus);
		System.setProperty("McRuntimeGameTestHolders", GametestConstants.MOD_ID);
//		eventBus.addListener(NeoforgeGameTestRegistry::onRegisterGameTests);
	}
	^///? }
	*///? }
}
//? }
