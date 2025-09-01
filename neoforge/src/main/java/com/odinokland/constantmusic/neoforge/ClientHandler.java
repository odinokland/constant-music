package com.odinokland.constantmusic.neoforge;

import com.odinokland.constantmusic.common.Constants;
import com.odinokland.constantmusic.common.util.JukeboxTrackerUtility;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.common.Mod;
//? if >= 1.20.6 {
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import java.util.function.Supplier;
//?} else {
/*import com.odinokland.constantmusic.common.gui.ConstantMusicConfigScreen;
import net.neoforged.neoforge.client.ConfigScreenHandler;
*///?}

/**
 * The type Client handler.
 */
//? if >= 1.21.6 {
@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
//?} else if >= 1.20.6 {
/*@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
*///?} else {
/*@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
*///?}
public class  ClientHandler {

	//? if >= 1.21.6 {
	/**
	 * Instantiates a new Client handler.
	 *
	 * @param modContainer the mod container
	 */
	public  ClientHandler(ModContainer modContainer) {
		modContainer.registerExtensionPoint(IConfigScreenFactory.class, (Supplier<IConfigScreenFactory>) ClientConfigHelper::new);
	}
	//?} else {
	/*/^*
	 * Instantiates a new Client handler.
	 ^/
	public ClientHandler() {}
	*///?}
	//? if < 1.21.6 {
	/*/^*
	 * On fml client setup event.
	 *
	 * @param event the event
	 ^/
    @SubscribeEvent
    public static void onFMLClientSetupEvent(FMLClientSetupEvent event) {
		//? if >= 1.20.6 {
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, ClientConfigHelper::new);
		//?} else {
		/^ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> {
			return new ConfigScreenHandler.ConfigScreenFactory((minecraft, parent) -> new ConstantMusicConfigScreen(parent));
		});
		^///?}
    }
	*///?}
}