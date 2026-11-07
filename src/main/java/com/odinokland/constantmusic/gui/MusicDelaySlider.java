package com.odinokland.constantmusic.gui;

import com.odinokland.constantmusic.core.ConstantMusicManager;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Unique;
//? < 1.19.0 {
//import net.minecraft.network.chat.TranslatableComponent;
//? }

/**
 * Music delay slider.
 */
public class MusicDelaySlider extends AbstractSliderButton {
	/**
	 * Music delay slider.
	 *
	 * @param x      the x
	 * @param y      the y
	 * @param width  the width
	 * @param height the height
	 */
	public MusicDelaySlider(int x, int y, int width, int height) {
		super(x, y, width, height, getDisplayValue(), getSliderPercentage());
		setValueFromConfig();
	}

	/**
	 * Sets value from config.
	 */
	@Unique
	protected void setValueFromConfig() {
		this.value = toSliderValue((Integer) ConstantMusicManager.SINGLETON.getTimer());
		this.applyValue();
		this.updateMessage();
	}

	/**
	 * Minimum value inclusive.
	 * @return the minimum value inclusive
	 */
	@Unique
	private static int minInclusive() {
		return 0;
	}

	/**
	 * Maximum value inclusive.
	 * @return the maximum value inclusive
	 */
	@Unique
	private static int maxInclusive() {
		return ConstantMusicManager.MAX_TIMER;
	}

	/**
	 * To slider value double.
	 *
	 * @param integer the integer
	 * @return the double
	 */
	@Unique
	protected static double toSliderValue(Integer integer) {
		return (double) Mth.map((float) integer.intValue(), (float) minInclusive(), (float) maxInclusive(), 0.0F, 1.0F);
	}

	/**
	 * From slider value integer.
	 *
	 * @param d The percentage value of the slider
	 * @return Integer value for saving to config
	 */
	@Unique
	protected static Integer fromSliderValue(double d) {
		return Mth.floor(Mth.map(d, 0.0, 1.0, (double) minInclusive(), (double) maxInclusive()));
	}

	/**
	 * Get the display value for the slider.
	 *
	 * @return Component
	 */
	private static Component getDisplayValue() {
		Integer value = ConstantMusicManager.SINGLETON.getTimer();
		return value.equals(0)
				?
				//? >= 1.19.0 {
				Component.translatable("constantmusic.option")
				//? } else {
				//new TranslatableComponent("constantmusic.option")
				//? }
						.append(": ")
						.append(CommonComponents.OPTION_OFF)
				: ConstantMusicManager.timeDisplayText(value);
	}

	/**
	 * Get the percentage value for the slider.
	 *
	 * @return double
	 */
	private static double getSliderPercentage() {
		Integer value = ConstantMusicManager.SINGLETON.getTimer();
		return toSliderValue(value);
	}

	/**
	 * Update the message displayed on the slider.
	 */
	@Override
	protected void updateMessage() {
		this.setMessage(getDisplayValue());
	}

	/**
	 * Apply the value of the slider to the config.
	 */
	@Override
	protected void applyValue() {
		int value = (int) fromSliderValue(this.value);
		ConstantMusicManager.SINGLETON.setTimer(value);
	}

	/**
	 * Gets value.
	 *
	 * @return the value
	 */
	public double getValue() {
		return this.value;
	}
}
