package com.odinokland.endlessmusic.fabric;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import dev.architectury.event.events.client.ClientGuiEvent;
import com.odinokland.endlessmusic.EndlessMusic;
import com.odinokland.endlessmusic.EndlessMusicConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EndlessMusicImpl implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        if(EndlessMusic.isClothConfigLoaded())
            AutoConfig.register(EndlessMusicConfig.class, Toml4jConfigSerializer::new);
        ClientGuiEvent.DEBUG_TEXT_LEFT.register(EndlessMusic::addDebugText);
    }

    public static boolean isClothConfigLoaded() {
        return false;
        // return FabricLoader.getInstance().isModLoaded("cloth-config");
    }

    public static int readValue() {
        Toml toml = new Toml().read(new File(EndlessMusic.configFile.toString()));
        return toml.getLong("timer").intValue();
    }

    public static void writeValue(int value) {
        TomlWriter tomlWriter = new TomlWriter();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("timer", value);
            tomlWriter.write(map, new File(EndlessMusic.configFile.toString()));
        } catch (IOException e) {
            // throw new RuntimeException(e);
        }
    }
}
