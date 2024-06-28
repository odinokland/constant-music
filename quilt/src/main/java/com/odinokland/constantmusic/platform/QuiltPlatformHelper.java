package com.odinokland.constantmusic.platform;

import com.odinokland.constantmusic.platform.services.IPlatformHelper;
import org.quiltmc.loader.api.QuiltLoader;

import java.nio.file.Path;

public class QuiltPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return QuiltLoader.isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return QuiltLoader.isDevelopmentEnvironment();
    }

    @Override
    public boolean isClothConfigLoaded() {
        return QuiltLoader.isModLoaded("cloth-config");
    }

    @Override
    public Path getConfigFolder() {
        return QuiltLoader.getConfigDir();
    }
}
