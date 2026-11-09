package com.odinokland.constantmusic.gui;

import com.odinokland.constantmusic.core.ConstantMusicManager;
import com.odinokland.constantmusic.core.MusicFrequency;
import com.odinokland.constantmusic.util.TranslatableComponentHelper;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
//? if >= 1.19.0 {
import net.minecraft.client.OptionInstance;
//? }

import java.util.Arrays;

/**
 * The type Button generator.
 */
public class ButtonGenerator {
	//? if > 1.21.5 {
	/*private static final String FREQUENCY_LABEL = "options.music_frequency";
	*///? } else {
	private static final String FREQUENCY_LABEL = "constantmusic.options.music_frequency";
	//? }

	/**
	 * Default constructor.
	 */
	ButtonGenerator() {

	}

	/**
	 * Music delay slider music delay slider.
	 *
	 * @param x     the x
	 * @param y     the y
	 * @param width the width
	 * @return the music delay slider
	 */
	public static MusicDelaySlider musicDelaySlider(int x, int y, int width) {
		MusicDelaySlider button = new MusicDelaySlider(x, y, width, 20);
		ConstantMusicManager.SINGLETON.initializeSlider(button);
		return button;
	}

	/**
	 * Music frequency button cycle button.
	 *
	 * @param x     the x
	 * @param y     the y
	 * @param width the width
	 * @return the cycle button
	 */
	public static CycleButton<MusicFrequency> musicFrequencyButton(int x, int y, int width) {
		//? if >= 1.21.11 {
		/*return CycleButton.builder(MusicFrequency::caption, ConstantMusicManager.SINGLETON.getFrequency())
		*///? } else {
		return CycleButton.builder(MusicFrequency::caption)
				.withInitialValue(ConstantMusicManager.SINGLETON.getFrequency())
		//? }
				.withValues(MusicFrequency.values())
				//.displayOnlyValue()
				.create(
						x, y, width, 20,
						TranslatableComponentHelper.translatable(FREQUENCY_LABEL),
						(button, value) -> ConstantMusicManager.SINGLETON.setFrequency(value)
				);
	}
	//? if >= 1.19.0 {
	/**
	 * Gets a config option.
	 *
	 * @return the config option
	 */
	public static OptionInstance<Integer> musicDelaySliderOptionInstance() {
		return new OptionInstance<Integer>(
				"constantmusic.option",
				OptionInstance.noTooltip(),
				(component, integer) -> {
					return integer.equals(0) ? Component.translatable("options.generic_value", new Object[]{component, CommonComponents.OPTION_OFF}) : ConstantMusicManager.timeDisplayText(integer);
				},
				new IntRange(0, 600),
				ConstantMusicManager.SINGLETON.getTimer(),
				(integer) -> {
					ConstantMusicManager.SINGLETON.setTimer(Integer.parseInt(integer.toString()));
				}
		);
	}

	private static final Component MUSIC_FREQUENCY_TOOLTIP = Component.translatable("constantmusic.options.music_frequency.tooltip");

	/**
	 * Music frequency option instance option instance.
	 *
	 * @return the option instance
	 */
	public static OptionInstance<MusicFrequency> musicFrequencyOptionInstance() {
		return new OptionInstance<>(
				FREQUENCY_LABEL,
				OptionInstance.cachedConstantTooltip(MUSIC_FREQUENCY_TOOLTIP),
				(caption, value) -> value.caption(),
				new OptionInstance.Enum<>(Arrays.asList(MusicFrequency.values()), MusicFrequency.CODEC),
				ConstantMusicManager.SINGLETON.getFrequency(),
				ConstantMusicManager.SINGLETON::setFrequency
		);
	}
	//? }
}
