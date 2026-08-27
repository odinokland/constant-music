package com.odinokland.constantmusic.platform.forge;

//? forge {

import com.odinokland.constantmusic.Constants;
import com.odinokland.constantmusic.platform.Platform;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraft.SharedConstants;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ForgePlatform implements Platform {

	@Override
	public boolean isModLoaded(String modId) {
		return ModList.get().isLoaded(modId);
	}

	@Override
	public ModLoader loader() {
		return ModLoader.FORGE;
	}

	@Override
	public String mcVersion() {
		return SharedConstants.getCurrentVersion().getName();
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
//?}
