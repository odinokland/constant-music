package com.odinokland.constantmusic.platform;

import java.nio.file.Path;

/**
 * The interface Platform.
 */
public interface Platform {
	/**
	 * Is mod loaded boolean.
	 *
	 * @param modId the mod id
	 * @return the boolean
	 */
	boolean isModLoaded(String modId);

	/**
	 * Loader mod loader.
	 *
	 * @return the mod loader
	 */
	ModLoader loader();

	/**
	 * Mc version string.
	 *
	 * @return the string
	 */
	String mcVersion();

	/**
	 * Is development environment boolean.
	 *
	 * @return the boolean
	 */
	boolean isDevelopmentEnvironment();

	/**
	 * Is debug boolean.
	 *
	 * @return the boolean
	 */
	default boolean isDebug() {
		return isDevelopmentEnvironment();
	}

	/**
	 * Gets config file.
	 *
	 * @return the config file
	 */
	Path getConfigFile();

	/**
	 * The enum Mod loader.
	 */
	enum ModLoader {
		/**
		 * Fabric mod loader.
		 */
		FABRIC,
		/**
		 * Neoforge mod loader.
		 */
		NEOFORGE,
		/**
		 * Forge mod loader.
		 */
		FORGE,
		/**
		 * Quilt mod loader.
		 */
		QUILT
	}
}
