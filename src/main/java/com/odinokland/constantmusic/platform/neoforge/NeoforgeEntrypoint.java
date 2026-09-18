package com.odinokland.constantmusic.platform.neoforge;

//? neoforge {
/*//? if >=1.21.5 {
//import com.odinokland.constantmusic.platform.neoforge.gametest.NeoforgeGameTests;
//? }
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import com.odinokland.constantmusic.ConstantMusic;
import com.odinokland.constantmusic.Constants;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

/^*
 * The type Neoforge entrypoint. aHXcqJOABdlHUb1@
 ^/
@Mod(Constants.MOD_ID)
public class NeoforgeEntrypoint {
	private static ModContainer modContainerContext;
	/^*
	 * Instantiates a new Neoforge entrypoint.
	 *
	 * @param modEventBus The mod event bus.
	 * @param modContainer The mod container.
	 ^/
	public NeoforgeEntrypoint(IEventBus modEventBus, ModContainer modContainer) {
		modContainerContext = modContainer;
		ConstantMusic.init();
		modEventBus.addListener(NeoforgeEntrypoint::onClientSetup);
		//? if >=1.21.5 {
		/^NeoforgeGameTests.FUNCTIONS.register(modEventBus);
		//modEventBus.addListener(NeoforgeGameTests::registerTests);
		//modEventBus.addListener(GameTestRegistry::onRegisterGameTests);
		^///? }
	}

	/^*
	 * On client setup.
	 * @param event The FMLClientSetupEvent.
	 ^/
	public static void onClientSetup(FMLClientSetupEvent event)
	{
		NeoForgeClientEntrypoint.setupConfigScreen(modContainerContext);
	}
}
*///?}
