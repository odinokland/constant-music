package com.odinokland.constantmusic.platform.forge;

//? forge {

import com.odinokland.constantmusic.Constants;
import com.odinokland.constantmusic.gui.ConstantMusicConfigScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * The type Client handler.
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeClientEventSubscriber {

	/**
	 * On fml client setup event.
	 *
	 * @param event the event
	 */
	@SubscribeEvent
	public static void onFMLClientSetupEvent(FMLClientSetupEvent event) {
		//? if <1.20 {
		ModLoadingContext.get().registerExtensionPoint(
				net.minecraftforge.client.ConfigScreenHandler.ConfigScreenFactory.class,
				() -> new net.minecraftforge.client.ConfigScreenHandler.ConfigScreenFactory(
						(client, parent) -> new ConstantMusicConfigScreen(parent)
				)
		);
		//? } else {
		/*ModLoadingContext.get().registerConfigScreen(
				(client, parent) -> new ConstantMusicConfigScreen(parent)
		);
		*///? }
	}
}
//?}
