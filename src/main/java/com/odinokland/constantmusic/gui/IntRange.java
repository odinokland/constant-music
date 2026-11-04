package com.odinokland.constantmusic.gui;

import com.mojang.serialization.Codec;
import com.odinokland.constantmusic.core.ConstantMusicManager;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractWidget;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Function;
//? < 26.3 {
import java.util.function.Consumer;
//? }

/**
 * The type Int range.
 */
public record IntRange(int minInclusive, int maxInclusive, boolean applyValueImmediately) implements OptionInstance.IntRangeBase {
	/**
	 * Instantiates a new Int range.
	 *
	 * @param minInclusive the min inclusive
	 * @param maxInclusive the max inclusive
	 */
	public IntRange(final int minInclusive, final int maxInclusive) {
		this(minInclusive, maxInclusive, true);
	}
	//? > 1.19.2 {
	/*//? >= 26.3 {
	//public Function<OptionInstance<Integer>, AbstractWidget> createButton(OptionInstance.TooltipSupplier<Integer> tooltip, Options options, int x, int y, int width, final OptionInstance.ValueUpdateListener<? super Integer> onValueChanged) {
	//? } else {
	@Override
	public Function<OptionInstance<Integer>, AbstractWidget> createButton(OptionInstance.TooltipSupplier<Integer> tooltip, Options options, int x, int y, int width, Consumer<Integer> onValueChanged) {
	//? }
		return instance -> {
			OptionInstance.OptionInstanceSliderButton<Integer> button = new OptionInstance.OptionInstanceSliderButton<Integer>(
					options,
					x,
					y,
					width,
					20,
					instance,
					this,
					tooltip,
					//? >= 1.20.6 {
					/^onValueChanged,
					this.applyValueImmediately()
					^///? } else {
					onValueChanged
					//? }
			);
			ConstantMusicManager.SINGLETON.initializeSlider(button);
			return button;
		};
	}
	*///? } else {
	@NotNull
	@Override
	public Function<OptionInstance<Integer>, AbstractWidget> createButton(@NotNull OptionInstance.TooltipSupplier<Integer> tooltipSupplier, @NotNull Options options, int x, int y, int width) {
		return optionInstance -> new OptionInstanceSliderButton<Integer>(options, x, y, width, 20, optionInstance, this, tooltipSupplier);
	}
	//? }

	/**
	 * Validate value optional.
	 *
	 * @param value the value
	 * @return the optional
	 */
	@NotNull
	@Override
	public Optional<Integer> validateValue(final Integer value) {
		return value.compareTo(this.minInclusive()) >= 0 && value.compareTo(this.maxInclusive()) <= 0 ? Optional.of(value) : Optional.empty();
	}

	/**
	 * Codec codec.
	 *
	 * @return the codec
	 */
	@NotNull
	@Override
	public Codec<Integer> codec() {
		return Codec.intRange(this.minInclusive, this.maxInclusive + 1);
	}

	public int minInclusive() {
		return this.minInclusive;
	}

	public int maxInclusive() {
		return this.maxInclusive;
	}
}
