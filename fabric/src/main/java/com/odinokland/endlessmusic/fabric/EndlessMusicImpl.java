package com.odinokland.endlessmusic.fabric;

import dev.architectury.event.events.client.ClientGuiEvent;
import com.odinokland.endlessmusic.EndlessMusic;
import com.odinokland.endlessmusic.EndlessMusicConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class EndlessMusicImpl implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        if(EndlessMusic.isClothConfigLoaded())
            AutoConfig.register(EndlessMusicConfig.class, Toml4jConfigSerializer::new);
        ClientGuiEvent.DEBUG_TEXT_LEFT.register(EndlessMusic::addDebugText);
    }

    public static boolean isClothConfigLoaded() {
        return FabricLoader.getInstance().isModLoaded("cloth-config");
    }
}
