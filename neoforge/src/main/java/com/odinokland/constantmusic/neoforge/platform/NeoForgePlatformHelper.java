package com.odinokland.constantmusic.neoforge.platform;

import com.odinokland.constantmusic.common.platform.services.IPlatformHelper;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;

public class NeoForgePlatformHelper implements IPlatformHelper {
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
	public Path getConfigFolder() {
		return FMLPaths.CONFIGDIR.get();
	}
}