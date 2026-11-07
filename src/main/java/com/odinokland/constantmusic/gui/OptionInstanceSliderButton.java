package com.odinokland.constantmusic.gui;

//? >= 1.19.0 && <= 1.19.2 {
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractOptionSliderButton;
import net.minecraft.client.gui.components.TooltipAccessor;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

/**
 * The type Option instance slider button.
 *
 * @param <N> the type parameter
 */
//
public class OptionInstanceSliderButton<N> extends AbstractOptionSliderButton /*$ option_implementation */{
	private final OptionInstance<N> instance;
	private final OptionInstance.SliderableValueSet<N> values;
	private final OptionInstance.TooltipSupplier<N> tooltip;

	/**
	 * Instantiates a new Option instance slider button.
	 *
	 * @param p_231701_ the p 231701
	 * @param p_231702_ the p 231702
	 * @param p_231703_ the p 231703
	 * @param p_231704_ the p 231704
	 * @param p_231705_ the p 231705
	 * @param p_231706_ the p 231706
	 * @param p_231707_ the p 231707
	 * @param p_231708_ the p 231708
	 */
	OptionInstanceSliderButton(Options p_231701_, int p_231702_, int p_231703_, int p_231704_, int p_231705_, OptionInstance<N> p_231706_, OptionInstance.SliderableValueSet<N> p_231707_, OptionInstance.TooltipSupplier<N> p_231708_) {
		super(p_231701_, p_231702_, p_231703_, p_231704_, p_231705_, p_231707_.toSliderValue(p_231706_.get()));
		this.instance = p_231706_;
		this.values = p_231707_;
		this.tooltip = p_231708_;
		this.updateMessage();
	}

	protected void updateMessage() {
		this.setMessage(this.instance.toString.apply(this.instance.get()));
	}

	protected void applyValue() {
		this.instance.set(this.values.fromSliderValue(this.value));
		//this.options.save();
	}

	/**
	 * Gets tooltip.
	 *
	 * @return the tooltip
	 */
	public List<FormattedCharSequence> getTooltip() {
		return this.tooltip.apply(this.values.fromSliderValue(this.value));
	}
}
//? }
