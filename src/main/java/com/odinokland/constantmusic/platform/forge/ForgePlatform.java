package com.odinokland.constantmusic.platform.forge;

//? forge {

import com.odinokland.constantmusic.Constants;
import com.odinokland.constantmusic.platform.Platform;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The type Forge platform.
 */
public class ForgePlatform implements Platform {
	/**
	 * Constructor for the ForgePlatform.
	 */
	public ForgePlatform() {
	}

	/**
	 * Is mod loaded.
	 * @param modId the mod id
	 * @return if the mod is loaded
	 */
	@Override
	public boolean isModLoaded(String modId) {
		//~ forge_update
		return ModList.get().isLoaded(modId);
	}

	/**
	 * Get the mod loader.
	 * @return the mod loader
	 */
	@Override
	public ModLoader loader() {
		return ModLoader.FORGE;
	}

	/**
	 * Get the minecraft version.
	 * @return the minecraft version
	 */
	@Override
	public String mcVersion() {
		return ModList.get()
				.getModContainerById("minecraft")
				.map(container -> container.getModInfo().getVersion().toString())
				.orElse("unknown");
	}

	/**
	 * Is development environment.
	 * @return if the environment is development
	 */
	@Override
	public boolean isDevelopmentEnvironment() {
		return !FMLLoader.isProduction();
	}

	/**
	 * Get the config file.
	 * @return the config file
	 */
	@Override
	public Path getConfigFile() {
		return Paths.get(FMLPaths.CONFIGDIR.get().toString(), Constants.MOD_ID + ".toml");
	}
}
//?}
