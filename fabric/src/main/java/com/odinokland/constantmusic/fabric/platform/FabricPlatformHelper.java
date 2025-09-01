package com.odinokland.constantmusic.fabric.platform;

import com.odinokland.constantmusic.common.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

/**
 * The type Fabric platform helper.
 */
public class FabricPlatformHelper implements IPlatformHelper {

	/**
	 * Gets platform name.
	 *
	 * @return the platform name
	 */
	@Override
	public String getPlatformName() {
		return "Fabric";
	}

	/**
	 * Is mod loaded boolean.
	 *
	 * @param modId the mod id
	 * @return the boolean
	 */
	@Override
	public boolean isModLoaded(String modId) {

		return FabricLoader.getInstance().isModLoaded(modId);
	}

	/**
	 * Is development environment boolean.
	 *
	 * @return the boolean
	 */
	@Override
	public boolean isDevelopmentEnvironment() {

		return FabricLoader.getInstance().isDevelopmentEnvironment();
	}

	/**
	 * Gets config folder.
	 *
	 * @return the config folder
	 */
	@Override
	public Path getConfigFolder() {
		return FabricLoader.getInstance().getConfigDir();
	}
}
