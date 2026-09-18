package com.odinokland.constantmusic;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import com.odinokland.constantmusic.platform.Platform;
//? fabric {
//import com.odinokland.constantmusic.platform.fabric.FabricPlatform;
//? } forge {
import com.odinokland.constantmusic.platform.forge.ForgePlatform;
//? } neoforge {
//import com.odinokland.constantmusic.platform.neoforge.NeoforgePlatform;
//? }
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Constant music.
 */
public class ConstantMusic {
	private static boolean manualConfigInitialized = false;
	private static int timer = 0;
	/**
	 * The constant MAX_TIMER.
	 */
	public static int MAX_TIMER = 600;
	private static final Platform PLATFORM = createPlatformInstance();

	/**
	 * Init.
	 */
	public static void init() {
		if (ConstantMusic.PLATFORM.isModLoaded(Constants.MOD_ID)) {
			//Constants.LOG.info("Hello to CONSTANT MUSIC");
		}
	}

	/**
	 * Read value int from file.
	 *
	 * @param file the config file
	 * @return the int
	 */
	public static int readValue(File file) {
		if (file == null || !file.exists()) {
			return 0;
		}
		try {
			Toml toml = new Toml().read(file);
			Long timerVal = toml.getLong("timer");
			return timerVal != null ? timerVal.intValue() : 0;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * Read value int.
	 *
	 * @return the int
	 */
	public static int readValue() {
		return readValue(new File(PLATFORM.getConfigFile().toString()));
	}

	/**
	 * Write value to file.
	 *
	 * @param file  the config file
	 * @param value the value
	 */
	public static void writeValue(File file, int value) {
		TomlWriter tomlWriter = new TomlWriter();
		try {
			if (file.getParentFile() != null) {
				file.getParentFile().mkdirs();
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("timer", value);
			tomlWriter.write(map, file);
		} catch (IOException e) {
			// ignore or log
		}
	}

	/**
	 * Write value.
	 *
	 * @param value the value
	 */
	public static void writeValue(int value) {
		writeValue(new File(PLATFORM.getConfigFile().toString()), value);
	}

	/**
	 * Reset manual config initialization flag for testing.
	 *
	 * @param defaultTimer the default timer value
	 */
	public static void resetForTesting(int defaultTimer) {
		timer = defaultTimer;
		manualConfigInitialized = true;
	}

	/**
	 * Reset manual config initialization state so next getTimer() reloads from file.
	 */
	public static void resetConfig() {
		manualConfigInitialized = false;
	}

	/**
	 * Gets timer.
	 *
	 * @return the timer
	 */
	public static int getTimer() {
		if (!manualConfigInitialized) {
			try {
				timer = readValue();
				manualConfigInitialized = true;
			} catch (Exception e) {
				return 0;
			}
		}
		return timer;
	}

	/**
	 * Sets timer.
	 *
	 * @param value the value
	 */
	public static void setTimer(int value) {
		writeValue(value);
		timer = value;
	}


	/**
	 * Time display text-mutable component.
	 *
	 * @param seconds the seconds
	 * @return the mutable component
	 */
	public static MutableComponent timeDisplayText(Integer seconds) {
		int minutes = seconds / 60;
		int remainingSeconds = seconds % 60;
		if (minutes > 0) {
			return Component.translatable("constantmusic.option.minutes_and_seconds", new Object[]{minutes, remainingSeconds});
		}
		return Component.translatable("constantmusic.option.seconds", new Object[]{seconds});

	}

	/**
	 * Create a platform instance.
	 *
	 * @return the platform
	 */
	private static Platform createPlatformInstance() {
		//? fabric {
		//return new FabricPlatform();
		//?} neoforge {
		//return new NeoforgePlatform();
		//?} forge {
		return new ForgePlatform();
		//?}
	}
}
