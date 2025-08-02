package com.odinokland.constantmusic.neoforge;

import com.odinokland.constantmusic.common.Constants;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
//? if >= 1.20.6 {
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
//?} else {
/*import com.odinokland.constantmusic.common.gui.ConstantMusicConfigScreen;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.ConfigScreenHandler;
*///?}

//? if >= 1.20.6 {
@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
//?} else {
/*@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
*///?}
public class  ClientHandler {
    @SubscribeEvent
    public static void onFMLClientSetupEvent(FMLClientSetupEvent event) {
		//? if >= 1.20.6 {
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, ClientConfigHelper::new);
		//?} else {
		/*ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> {
			return new ConfigScreenHandler.ConfigScreenFactory((minecraft, parent) -> new ConstantMusicConfigScreen(parent));
		});
		*///?}
    }
}