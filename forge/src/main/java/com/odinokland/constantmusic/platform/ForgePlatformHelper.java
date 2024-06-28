package com.odinokland.constantmusic.platform;

import com.odinokland.constantmusic.ClientHandler;
import com.odinokland.constantmusic.platform.services.IPlatformHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class ForgePlatformHelper implements IPlatformHelper {

    public ForgePlatformHelper() {
//        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> IExtensionPoint.DisplayTest.IGNORESERVERONLY, (a, b) -> true));
//        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientHandler::setupConfig);
    }

    @Override
    public String getPlatformName() {

        return "Forge";
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
        // Return false until cloth_config gets a Forge 1.21 update
        return false;
        //return ModList.get().isLoaded("cloth_config");
    }

    @Override
    public Path getConfigFolder() {
        return FMLPaths.CONFIGDIR.get();
    }
}