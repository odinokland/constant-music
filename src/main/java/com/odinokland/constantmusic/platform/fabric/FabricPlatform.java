package com.odinokland.constantmusic.platform.fabric;

//? fabric {

import com.odinokland.constantmusic.Constants;
import com.odinokland.constantmusic.platform.Platform;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FabricPlatform implements Platform {

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
	 * Gets platform name.
	 *
	 * @return the platform name
	 */
	@Override
	public ModLoader loader() {
		return ModLoader.FABRIC;
	}

	/**
	 * Gets mc version.
	 * @return the mc version
	 */
	@Override
	public String mcVersion() {
		return FabricLoader.getInstance().getRawGameVersion();
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
	public Path getConfigFile() {
		return Paths.get(FabricLoader.getInstance().getConfigDir().toString(), Constants.MOD_ID + ".toml");
	}
}
//?}
