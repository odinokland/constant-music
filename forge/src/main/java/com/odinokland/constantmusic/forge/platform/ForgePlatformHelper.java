package com.odinokland.constantmusic.forge.platform;

import com.odinokland.constantmusic.common.platform.services.IPlatformHelper;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

/**
 * The type Forge platform helper.
 */
public class ForgePlatformHelper implements IPlatformHelper {

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
	public Path getConfigFolder() {
		return FMLPaths.CONFIGDIR.get();
	}
}