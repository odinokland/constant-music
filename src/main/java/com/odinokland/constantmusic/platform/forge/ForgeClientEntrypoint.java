package com.odinokland.constantmusic.platform.forge;

//? if forge {
import com.odinokland.constantmusic.util.Constants;
import com.odinokland.constantmusic.gui.ConfigScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
//? if < 1.21.1 {
import net.minecraftforge.fml.ModLoadingContext;
//? }
//? if >=1.19.0 {
import net.minecraftforge.client.ConfigScreenHandler;
//? } else {
/*import net.minecraftforge.client.ConfigGuiHandler;
*///? }
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
	 *
	 * @param event The FMLClientSetupEvent for the mod.
	 */
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			//? if < 1.19.0 {
			/*ModLoadingContext.get().registerExtensionPoint(ConfigGuiHandler.ConfigGuiFactory.class,
					() -> new ConfigGuiHandler.ConfigGuiFactory(
							(client, parent) -> new ConfigScreen(parent)));
			*///? } elif < 1.21.1 {
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
