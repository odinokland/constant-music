package com.odinokland.constantmusic.platform.neoforge;

//? neoforge {
/*import net.neoforged.bus.api.IEventBus;
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
