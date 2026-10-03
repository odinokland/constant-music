package com.odinokland.constantmusic.platform.forge;

//? forge {
import com.odinokland.constantmusic.Constants;
import com.odinokland.constantmusic.gui.ConfigScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * Entry point for Forge client-side code.
 */
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ForgeClientEntrypoint {

	/**
	 * Default constructor for ForgeClientEntrypoint.
	 */
	public ForgeClientEntrypoint() {
	}
	/**
	 * Hook to set up config screen.
	 * @param event The FMLClientSetupEvent for the mod.
	 */
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			//? if < 1.21.1 {
			// Register the config screen
			ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
					() -> new ConfigScreenHandler.ConfigScreenFactory(
							(client, parent) -> new ConfigScreen(parent)));
			//? } else {
			/*// Register the config screen
			ForgeEntrypoint.INSTANCE.context.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
					() -> new ConfigScreenHandler.ConfigScreenFactory(
							(client, parent) -> new ConfigScreen(parent)));
			*///? }
		});
	}
}
//?}
