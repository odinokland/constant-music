package com.odinokland.constantmusic;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ClientHandler {
    @SubscribeEvent
    public static void onFMLClientSetupEvent(FMLClientSetupEvent event) {
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, ClientConfigHelper::new);
    }
}