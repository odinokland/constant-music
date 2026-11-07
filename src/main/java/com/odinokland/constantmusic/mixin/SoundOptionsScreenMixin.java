package com.odinokland.constantmusic.mixin;

import com.odinokland.constantmusic.core.ConstantMusicManager;
import com.odinokland.constantmusic.gui.ButtonGenerator;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
//? < 1.19.0 {
/*import net.minecraft.client.Option;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.VolumeSlider;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.sounds.SoundSource;
*///? } else if < 1.19.3 {
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.components.AbstractWidget;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import java.util.function.BiFunction;
//? } else {
//import net.minecraft.client.OptionInstance;
//? }
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.Screen;
//? >= 1.21.1 {
//import net.minecraft.client.gui.screens.options.OptionsSubScreen;
//? } else {
import net.minecraft.client.gui.screens.OptionsSubScreen;
//? }
import net.minecraft.client.gui.screens.SoundOptionsScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
//? > 1.21.5 {
/*import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
*///? } else {
import net.minecraft.client.gui.components.OptionsList;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//? }

/**
 * The type Sound options screen mixin.
 */
@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(SoundOptionsScreen.class)
public abstract class SoundOptionsScreenMixin extends OptionsSubScreen {
	//? > 1.19.2 && < 1.21.1 {
	/*@Shadow
	private OptionsList list;
	*///? }

	/**
	 * Instantiates a new Sound options screen mixin.
	 *
	 * @param lastScreen the last screen
	 * @param options    the options
	 * @param title      the title
	 */
	protected SoundOptionsScreenMixin(Screen lastScreen, Options options, Component title) {
		super(lastScreen, options, title);
	}

	//? if > 1.21.5 {
	/*@ModifyArgs(
			method = "addOptions",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/components/OptionsList;addSmall([Lnet/minecraft/client/OptionInstance;)V"
			)
	)
	private void modifyAddSmallArgs(Args args) {
		OptionInstance<?>[] originalOptions = args.get(0);

		if (originalOptions != null && originalOptions.length == 2) {
			OptionInstance<?> secondOption = originalOptions[1];
			//? >= 1.21.11 {
			//boolean checkSecondOption = secondOption == this.options.musicToast();
			//? } else {
			boolean checkSecondOption = secondOption == this.options.showNowPlayingToast();
			//? }
			if (checkSecondOption) {
				OptionInstance<?>[] customRows = new OptionInstance<?>[] {
						ButtonGenerator.musicFrequencyOptionInstance(),
						ButtonGenerator.musicDelaySliderOptionInstance(),
						secondOption, // musicToast
						null
				};

				// Replace the original 2-element array with our new 4-element array
				args.set(0, customRows);
			}
		}
	}

	*///? } else if >=1.19.3 {

	/*@Inject(
			//~ if >= 1.21.1 'init' -> 'addOptions'
			method = "init",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/components/OptionsList;addSmall([Lnet/minecraft/client/OptionInstance;)V",
					ordinal = 0,
					shift = At.Shift.AFTER
			)
	)
	private void addNewOptions(CallbackInfo ci) {
		OptionInstance<?>[] newOptions = {
				ButtonGenerator.musicFrequencyOptionInstance(),
				ButtonGenerator.musicDelaySliderOptionInstance()
		};
		assert this.list != null;
		this.list.addSmall(newOptions);
	}
	*///?} else if >= 1.19.0 {
	@Inject(
			method = "init",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/Options;soundDevice()Lnet/minecraft/client/OptionInstance;",
					shift = At.Shift.BEFORE
			),
			require = 1
	)
	private void mymod$addCustomOptions(
			CallbackInfo ci,
			@Local(name = "i") int i,
			@Local(index = 3) LocalIntRef k
	) {
		int internalK = k.get();

		constantmusic$addWidgets(
				i,
				internalK,
				(xPos, yPos) -> ButtonGenerator.musicFrequencyButton(xPos, yPos, 150)
		);
		constantmusic$addWidgets(
				i,
				++internalK,
				(xPos, yPos) -> ButtonGenerator.musicDelaySlider(xPos, yPos, 150)
		);

		// Reserve one full row for the custom widget.
		k.set(k.get() + 2);
	}

	@Unique
	private void constantmusic$addWidgets(int i, int k, BiFunction<Integer, Integer, AbstractWidget> widget) {
		int x = this.width / 2 - 155 + k % 2 * 160;
		int y = i + 22 * (k >> 1);
		this.addRenderableWidget(widget.apply(x, y));
	}
	//?} else {
	/*@Override
	/^? fabric { ^//^public^//^? } else { ^/protected/^? }^/ void init() {
		int i = this.height / 6 - 12;
		int j = 22;
		int k = 0;
		this.addRenderableWidget(new VolumeSlider(this.minecraft, this.width / 2 - 155 + k % 2 * 160, i + 22 * (k >> 1), SoundSource.MASTER, 310));
		k += 2;

		for(SoundSource soundsource : SoundSource.values()) {
			if (soundsource != SoundSource.MASTER) {
				this.addRenderableWidget(new VolumeSlider(this.minecraft, this.width / 2 - 155 + k % 2 * 160, i + 22 * (k >> 1), soundsource, 150));
				++k;
			}
		}

		if (k % 2 == 1) {
			++k;
		}
		this.addRenderableWidget(ButtonGenerator.musicFrequencyButton(this.width / 2 - 155 + k % 2 * 160,i + 22 * (k >> 1), 150));
		++k;
		this.addRenderableWidget(ButtonGenerator.musicDelaySlider(this.width / 2 - 155 + k % 2 * 160,i + 22 * (k >> 1), 150));
		++k;

		this.addRenderableWidget(Option.AUDIO_DEVICE.createButton(this.options, this.width / 2 - 155, i + 22 * (k >> 1), 310));
		k += 2;
		this.addRenderableWidget(Option.SHOW_SUBTITLES.createButton(this.options, this.width / 2 - 75, i + 22 * (k >> 1), 150));
		k += 2;
		this.addRenderableWidget(new Button(this.width / 2 - 100, i + 22 * (k >> 1), 200, 20, CommonComponents.GUI_DONE, (p_96713_) -> {
			this.minecraft.setScreen(this.lastScreen);
		}));
	}
	*///? }

	@Override
	public void onClose() {
		super.onClose();
		ConstantMusicManager.SINGLETON.clearSlider();
	}
}
