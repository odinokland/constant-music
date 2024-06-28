package com.odinokland.constantmusic.platform;

import com.odinokland.constantmusic.ConstantMusicConfig;
import com.odinokland.constantmusic.platform.services.IPlatformHelper;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import java.nio.file.Path;

public class NeoForgePlatformHelper implements IPlatformHelper {
    public NeoForgePlatformHelper() {
        AutoConfig.register(ConstantMusicConfig.class, Toml4jConfigSerializer::new);
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () ->
                (mc, parent) -> AutoConfig.getConfigScreen(ConstantMusicConfig.class, parent).get()
        );
    }

    @Override
    public String getPlatformName() {

        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    @Override
    public boolean isClothConfigLoaded() {
        return ModList.get().isLoaded("cloth_config");
    }

    @Override
    public Path getConfigFolder() {
        return FMLPaths.CONFIGDIR.get();
    }
}