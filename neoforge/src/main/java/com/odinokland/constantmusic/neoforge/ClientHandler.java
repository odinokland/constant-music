package com.odinokland.constantmusic.neoforge;

import com.odinokland.constantmusic.common.Constants;
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

//? if >= 1.21.6 {
@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
//?} else if >= 1.20.6 {
/*@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
*///?} else {
/*@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
*///?}
public class  ClientHandler {
	//? if >= 1.21.6 {
	public  ClientHandler(ModContainer modContainer) {
		modContainer.registerExtensionPoint(IConfigScreenFactory.class, (Supplier<IConfigScreenFactory>) ClientConfigHelper::new);
	}
	//?}
	//? if < 1.21.6 {
    /*@SubscribeEvent
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