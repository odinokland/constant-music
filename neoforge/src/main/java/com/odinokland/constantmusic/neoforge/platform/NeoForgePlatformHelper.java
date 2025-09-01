package com.odinokland.constantmusic.neoforge.platform;

import com.odinokland.constantmusic.common.platform.services.IPlatformHelper;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;

/**
 * The type Neo forge platform helper.
 */
public class NeoForgePlatformHelper implements IPlatformHelper {
	/**
	 * Instantiates a new Neo forge platform helper.
	 */
	public NeoForgePlatformHelper() {}

	/**
	 * Gets platform name.
	 *
	 * @return the platform name
	 */
	@Override
	public String getPlatformName() {

		return "NeoForge";
	}

	/**
	 * Is mod loaded boolean.
	 *
	 * @param modId the mod id
	 * @return the boolean
	 */
	@Override
	public boolean isModLoaded(String modId) {

		return ModList.get().isLoaded(modId);
	}

	/**
	 * Is development environment boolean.
	 *
	 * @return the boolean
	 */
	@Override
	public boolean isDevelopmentEnvironment() {

		return !FMLLoader.isProduction();
	}

	/**
	 * Gets config folder.
	 *
	 * @return the config folder
	 */
	@Override
	public Path getConfigFolder() {
		return FMLPaths.CONFIGDIR.get();
	}
}