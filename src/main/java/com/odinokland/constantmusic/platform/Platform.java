package com.odinokland.constantmusic.platform;

import java.nio.file.Path;

public interface Platform {
	boolean isModLoaded(String modId);

	ModLoader loader();

	String mcVersion();

	boolean isDevelopmentEnvironment();

	default boolean isDebug() {
		return isDevelopmentEnvironment();
	}

	Path getConfigFile();

	enum ModLoader {
		FABRIC, NEOFORGE, FORGE, QUILT
	}
}
