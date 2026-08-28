package com.odinokland.constantmusic;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import com.odinokland.constantmusic.platform.Platform;
//? fabric {
import com.odinokland.constantmusic.platform.fabric.FabricPlatform;
//? } forge {
//import com.odinokland.constantmusic.platform.forge.ForgePlatform;
//? } neoforge {
//import com.odinokland.constantmusic.platform.neoforge.NeoforgePlatform;
//? }
import net.minecraft.client.OptionInstance;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConstantMusic {
	private static boolean manualConfigInitialized = false;
	private static int timer = 0;
	public static int MAX_TIMER = 600;
	private static final Platform PLATFORM = createPlatformInstance();

	// The loader specific projects are able to import and use any code from the common project. This allows you to
	// write the majority of your code here and load it from your loader specific projects. This example has some
	// code that gets invoked by the entry point of the loader specific projects.
	public static void init() {

		// Constants.LOG.info("Hello from Common init on {}! we are currently in a {} environment!", ConstantMusic.PLATFORM.getPlatformName(), ConstantMusic.PLATFORM.getEnvironmentName());
		// Constants.LOG.info("The ID for diamonds is {}", BuiltInRegistries.ITEM.getKey(Items.DIAMOND));

		// It is common for all supported loaders to provide a similar feature that can not be used directly in the
		// common code. A popular way to get around this is using Java's built-in service loader feature to create
		// your own abstraction layer. You can learn more about this in our provided services class. In this example
		// we have an interface in the common code and use a loader specific implementation to delegate our call to
		// the platform specific approach.
		if (ConstantMusic.PLATFORM.isModLoaded(Constants.MOD_ID)) {
			//Constants.LOG.info("Hello to CONSTANT MUSIC");
		}
	}
	/**
	 * Read value int.
	 *
	 * @return the int
	 */
	public static int readValue() {
		Toml toml = new Toml().read(new File(PLATFORM.getConfigFile().toString()));
		return toml.getLong("timer").intValue();
	}

	/**
	 * Write value.
	 *
	 * @param value the value
	 */
	public static void writeValue(int value) {
		TomlWriter tomlWriter = new TomlWriter();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("timer", value);
			tomlWriter.write(map, new File(PLATFORM.getConfigFile().toString()));
		} catch (IOException e) {
			// throw new RuntimeException(e);
		}
	}

	/**
	 * Gets timer.
	 *
	 * @return the timer
	 */
	public static int getTimer() {
		if (!manualConfigInitialized) {
			try {
				timer =  readValue();
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
	 * Gets a config option.
	 *
	 * @return the config option
	 */
	public static OptionInstance<Integer> getConfigOption() {
		return new OptionInstance<Integer>("constantmusic.option", OptionInstance.noTooltip(), (component, integer) -> {
			return integer.equals(0) ? Component.translatable("options.generic_value", new Object[]{component, CommonComponents.OPTION_OFF}) : ConstantMusic.timeDisplayText(integer);
		}, new OptionInstance.IntRange(0, 600), ConstantMusic.getTimer(), (integer) -> {
			ConstantMusic.setTimer(Integer.parseInt(integer.toString()));
		});
	}

	/**
	 * Time display text-mutable component.
	 *
	 * @param seconds the seconds
	 * @return the mutable component
	 */
	public static MutableComponent timeDisplayText(Integer seconds) {
		int minutes = seconds/60;
		int remainingSeconds = seconds%60;
		if (minutes > 0) {
			return Component.translatable("constantmusic.option.minutes_and_seconds", new Object[]{minutes, remainingSeconds});
		}
		return Component.translatable("constantmusic.option.seconds", new Object[]{seconds});

	}

	/**
	 * Create a platform instance.
	 * @return the platform
	 */
	private static Platform createPlatformInstance() {
		//? fabric {
		return new FabricPlatform();
		//?} neoforge {
		//return new NeoforgePlatform();
		//?} forge {
		//return new ForgePlatform();
		//?}
	}
}
