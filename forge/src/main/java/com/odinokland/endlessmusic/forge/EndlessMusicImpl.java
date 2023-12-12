package com.odinokland.endlessmusic.forge;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import com.odinokland.endlessmusic.EndlessMusic;
import com.odinokland.endlessmusic.EndlessMusicConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.IExtensionPoint;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Mod(EndlessMusic.MODID)
public class EndlessMusicImpl {

    public EndlessMusicImpl() {
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> IExtensionPoint.DisplayTest.IGNORESERVERONLY, (a, b) -> true));
        if(EndlessMusic.isClothConfigLoaded())
            DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientHandler::setupConfig);
    }

    public static boolean isClothConfigLoaded() {
        return ModList.get().isLoaded("cloth_config");
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
