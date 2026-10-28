package com.odinokland.constantmusic.platform.neoforge;

//? neoforge {

/*import com.odinokland.constantmusic.Constants;
import com.odinokland.constantmusic.platform.Platform;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;
import java.nio.file.Paths;

/^*
 * The type Neoforge platform.
 ^/
public class NeoforgePlatform implements Platform {
	/^*
	 * Default constructor.
	 ^/
	public NeoforgePlatform() {
	}

	/^*
	 * On mod loaded.
	 * @param modId the mod id
	 * @return true if the mod is loaded, false otherwise
	 ^/
	@Override
	public boolean isModLoaded(String modId) {
		return ModList.get().isLoaded(modId);
	}

	/^*
	 * On mod loaded.
	 * @return the mod loader
	 ^/
	@Override
	public ModLoader loader() {
		return ModLoader.NEOFORGE;
	}

	//~ if >1.21.8 'FMLLoader.' -> 'FMLLoader.getCurrent().' {
	/^*
	 * Get the minecraft version.
	 * @return the minecraft version
	 ^/
	@Override
	public String mcVersion() {
		//~ if >1.21.8 'versionInfo()' -> 'getVersionInfo()'
		return FMLLoader.versionInfo().mcVersion();
	}

	/^*
	 * Is development environment.
	 * @return true if the environment is development, false otherwise
	 ^/
	@Override
	public boolean isDevelopmentEnvironment() {
		return !FMLLoader.isProduction();
	}
	//~}

	/^*
	 * Get the config file.
	 * @return the config file
	 ^/
	@Override
	public Path getConfigFile() {
		return Paths.get(FMLPaths.CONFIGDIR.get().toString(), Constants.MOD_ID + ".toml");
	}
}
*///?}
