package com.odinokland.endlessmusic.forge;

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
}
