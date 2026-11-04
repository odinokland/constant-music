package com.odinokland.constantmusic.gui;

//? <= 1.19.2 {
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractOptionSliderButton;
import net.minecraft.client.gui.components.TooltipAccessor;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

//
public class OptionInstanceSliderButton<N> extends AbstractOptionSliderButton /*$ option_implementation */{
	private final OptionInstance<N> instance;
	private final OptionInstance.SliderableValueSet<N> values;
	private final OptionInstance.TooltipSupplier<N> tooltip;

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

	public List<FormattedCharSequence> getTooltip() {
		return this.tooltip.apply(this.values.fromSliderValue(this.value));
	}
}
//? }
