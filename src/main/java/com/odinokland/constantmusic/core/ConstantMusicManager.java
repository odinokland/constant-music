package com.odinokland.constantmusic.core;

import com.odinokland.constantmusic.client.ClientProxy;
import com.odinokland.constantmusic.gui.ConfigScreen;
import com.odinokland.constantmusic.platform.Platform;
//? fabric {
//import com.odinokland.constantmusic.platform.fabric.FabricPlatform;
//? } forge {
import com.odinokland.constantmusic.platform.forge.ForgePlatform;
//? } neoforge {
//import com.odinokland.constantmusic.platform.neoforge.NeoforgePlatform;
//? }
import com.odinokland.constantmusic.server.ServerProxy;
import com.odinokland.constantmusic.util.Constants;
import com.odinokland.constantmusic.util.IModProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * The type Constant music.
 */
public class ConstantMusicManager {
	private boolean isTesting = false;

	private ConfigManager fileHandler;
	private AbstractWidget sliderInstance;
	/**
	 * The constant MAX_TIMER.
	 */
	public static int MAX_TIMER = 600;
	private final Platform PLATFORM = createPlatformInstance();
	/**
	 * The constant SINGLETON.
	 */
	public static final ConstantMusicManager SINGLETON = new ConstantMusicManager();

	/**
	 * Default constructor for ConstantMusicManager.
	 */
	private ConstantMusicManager() {
		Constants.LOG.info("Creating ConstantMusicManager");
	}


	/**
	 * Init.
	 */
	public void init() {
		Constants.LOG.info("Initializing ConfigManager");
		fileHandler = new ConfigManager(getDefaultConfigFile());
		fileHandler.load();
	}

	/**
	 * Init with file.
	 */
	public void init(File file) {
		Constants.LOG.info("Initializing ConstantMusicManager with file: {}", file.getAbsolutePath());
		fileHandler = new ConfigManager(file);
		fileHandler.load();
	}

	@NotNull
	private File getDefaultConfigFile() {
		return new File(PLATFORM.getConfigFile().toString());
	}

	/**
	 * Reset manual config initialization flag for testing.
	 */
	public void resetForTesting() {
		isTesting = true;
		setTimer(0);
		setFrequency(MusicFrequency.DEFAULT);
	}

	/**
	 * Reset manual config initialization flag for testing.
	 *
	 * @param defaultTimer the default timer value
	 */
	public void resetForTesting(int defaultTimer) {
		isTesting = true;
		setTimer(defaultTimer);
		setFrequency(MusicFrequency.DEFAULT);
	}

	/**
	 * Reset manual config initialization flag for testing.
	 *
	 * @param defaultTimer the default timer value
	 */
	public void resetForTesting(int defaultTimer, MusicFrequency defaultFrequency) {
		isTesting = true;
		setTimer(defaultTimer);
		setFrequency(defaultFrequency);
	}

	/**
	 * Gets timer.
	 *
	 * @return the timer
	 */
	public int getTimer() {
		return fileHandler.getData().timer;
	}

	/**
	 * Sets timer.
	 *
	 * @param value the value
	 */
	public void setTimer(int value) {
		fileHandler.getData().timer = value;
		fileHandler.save();
	}

	/**
	 * Gets frequency.
	 *
	 * @return the frequency
	 */
	public MusicFrequency getFrequency() {
		return MusicFrequency.valueOf(fileHandler.getData().frequency);
	}

	/**
	 * Sets frequency.
	 *
	 * @param value the value
	 */
	public void setFrequency(MusicFrequency value) {
		fileHandler.getData().frequency = value.name();
		fileHandler.save();
		setSliderActiveState();
		updateMinecraftOptions();
	}

	/**
	 * Set the base minecraft music frequency option
	 */
	private void updateMinecraftOptions() {
		//? > 1.21.5 {
		/*MusicFrequency frequency = getFrequency();
		if (frequency == MusicFrequency.FIXED) {
			if (getTimer() == 0) {
				frequency = MusicFrequency.CONSTANT;
			} else {
				frequency = MusicFrequency.FREQUENT;
			}
		}
		if (!isTesting) {
			IModProxy proxy = PLATFORM.isClient()
					? new ClientProxy()
					: new ServerProxy();
			proxy.updateMusicFrequencyOption(frequency.name());
		}
		*///? }
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
	 * Check if a mod is loaded
	 *
	 * @param modId the mod's id
	 * @return whether or not the mod is loaded
	 */
	public boolean isModLoaded(String modId) {
		return PLATFORM.isModLoaded(modId);
	}

	/**
	 * Create a platform instance.
	 *
	 * @return the platform
	 */
	private Platform createPlatformInstance() {
		//? fabric {
		//return new FabricPlatform();
		//?} neoforge {
		//return new NeoforgePlatform();
		//?} forge {
		return new ForgePlatform();
		//?}
	}

	/**
	 * Store a reference to the slider and set its active state.
	 *
	 * @param sliderInstance An instance of the slider
	 */
	public void initializeSlider(AbstractWidget sliderInstance) {
		this.sliderInstance = sliderInstance;
		setSliderActiveState();
	}

	/**
	 * Clear the reference to the slider
	 */
	public void clearSlider() {
		this.sliderInstance = null;
	}

	/**
	 * Set the active state of the slider.
	 */
	public void setSliderActiveState() {
		if (this.sliderInstance != null) {
			this.sliderInstance.active = getFrequency() == MusicFrequency.FIXED;
		}
	}

	/**
	 * Check if the manager is in testing mode.
	 *
	 * @return true if the manager is in testing mode, false otherwise
	 */
	public boolean isTesting() {
		return isTesting;
	}

}
