package com.odinokland.constantmusic.platform.forge;

//? forge {
/*
import com.odinokland.constantmusic.Constants;
import com.odinokland.constantmusic.platform.Platform;
//~ !forge_update
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;
import java.nio.file.Paths;

/^*
 * The type Forge platform.
 ^/
public class ForgePlatform implements Platform {

	@Override
	public boolean isModLoaded(String modId) {
		//~ forge_update
		return ModList.get().isLoaded(modId);
	}

	@Override
	public ModLoader loader() {
		return ModLoader.FORGE;
	}

	@Override
	public String mcVersion() {
		return ModList.get()
                .getModContainerById("minecraft")
                .map(container -> container.getModInfo().getVersion().toString())
                .orElse("unknown");
	}

	@Override
	public boolean isDevelopmentEnvironment() {
		return !FMLLoader.isProduction();
	}

	@Override
	public Path getConfigFile() {
		return Paths.get(FMLPaths.CONFIGDIR.get().toString(), Constants.MOD_ID + ".toml");
	}
}
*///?}
