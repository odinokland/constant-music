package com.odinokland.constantmusic.fabric;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import com.odinokland.constantmusic.ConstantMusic;
import com.odinokland.constantmusic.ConstantMusicConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ConstantMusicImpl implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        if(ConstantMusic.isClothConfigLoaded())
            AutoConfig.register(ConstantMusicConfig.class, Toml4jConfigSerializer::new);
    }

    public static boolean isClothConfigLoaded() {
       return FabricLoader.getInstance().isModLoaded("cloth-config");
    }

    public static int readValue() {
        Toml toml = new Toml().read(new File(ConstantMusic.configFile.toString()));
        return toml.getLong("timer").intValue();
    }

    public static void writeValue(int value) {
        TomlWriter tomlWriter = new TomlWriter();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("timer", value);
            tomlWriter.write(map, new File(ConstantMusic.configFile.toString()));
        } catch (IOException e) {
            // throw new RuntimeException(e);
        }
    }

    public static Path getConfigFolder() {
        return FabricLoader.getInstance().getConfigDir().toAbsolutePath().normalize();
    }
}
