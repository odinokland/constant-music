package com.odinokland.constantmusic.platform.neoforge;

//? neoforge {
/*
import com.odinokland.constantmusic.Constants;
import com.odinokland.constantmusic.platform.Platform;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;
import net.minecraft.SharedConstants;

import java.nio.file.Path;
import java.nio.file.Paths;

public class NeoforgePlatform implements Platform {
	@Override
	public boolean isModLoaded(String modId) {
		return ModList.get().isLoaded(modId);
	}

	@Override
	public ModLoader loader() {
		return ModLoader.NEOFORGE;
	}

	@Override
	public String mcVersion() {
		return SharedConstants.getCurrentVersion().getName();
	}

	@Override
	public boolean isDevelopmentEnvironment() {
		return !FMLLoader/^? if > 1.21.7 {^//^.getCurrent()^//^?}^/.isProduction();
	}

	@Override
	public Path getConfigFile() {
		return Paths.get(FMLPaths.CONFIGDIR.get().toString(), Constants.MOD_ID + ".toml");
	}
}
*///?}
