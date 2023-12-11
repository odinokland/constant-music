package com.odinokland.endlessmusic.forge;

import com.odinokland.endlessmusic.EndlessMusic;
import com.odinokland.endlessmusic.EndlessMusicConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = EndlessMusic.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientHandler {

    public static void setupConfig() {
        AutoConfig.register(EndlessMusicConfig.class, Toml4jConfigSerializer::new);
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () ->
                new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> AutoConfig.getConfigScreen(EndlessMusicConfig.class, parent).get())
        );
    }

    @SubscribeEvent
    public static void debugTextEvent(final CustomizeGuiOverlayEvent.DebugText event) {
        if(!event.getLeft().isEmpty())
            EndlessMusic.addDebugText(event.getLeft());
    }
}
