package com.odinokland.constantmusic.mixin;


import com.odinokland.constantmusic.ConstantMusic;
import com.odinokland.constantmusic.Constants;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import net.minecraft.client.OptionInstance;
//? < 1.19.3 {
/*import net.minecraft.client.gui.components.VolumeSlider;
import com.llamalad7.mixinextras.sugar.Local;
import com.odinokland.constantmusic.util.ICustomSoundSource;
import com.odinokland.constantmusic.util.SoundSourceUtil;
import net.minecraft.sounds.SoundSource;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
*///? }
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.SoundOptionsScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * The type Sound options screen mixin.
 */
@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(SoundOptionsScreen.class)
public class SoundOptionsScreenMixin extends Screen {

	protected SoundOptionsScreenMixin(Component title) {
		super(title);
		Constants.LOG.info("SoundOptionsScreenMixin constructor");
	}

	//? if >=1.19.3 {

	/**
	 * On get all options.
	 *
	 * @param cir the cir
	 */
	@Inject(method = "getAllSoundOptionsExceptMaster", at = @At("RETURN"), cancellable = true)
	protected void onGetAllOptions(CallbackInfoReturnable<OptionInstance<?>[]> cir) {
		OptionInstance<?>[] defaultOptions = cir.getReturnValue();

		OptionInstance<Integer> seconds = ConstantMusic.getConfigOption();

		OptionInstance<?>[] updatedOptions = new OptionInstance<?>[defaultOptions.length + 1];

		System.arraycopy(defaultOptions, 0, updatedOptions, 0, defaultOptions.length);

		updatedOptions[updatedOptions.length - 1] = seconds;

		cir.setReturnValue(updatedOptions);
	}
	//?} else {
/*
	/^*
	 * In 1.19.2, SoundOptionsScreen#init loops over SoundSource.values().
	 * We can intercept that array and swap it out for an array that has
	 * our custom size, OR we can capture the local loop counter.
	 ^/
	@Inject(
			method = "init",
			at = @At(
					value = "INVOKE",
					// This is the call inside the loop where it instantiates each VolumeSlider
					target = "Lnet/minecraft/client/gui/components/VolumeSlider;<init>(Lnet/minecraft/client/Minecraft;IILnet/minecraft/sounds/SoundSource;I)V",
					shift = At.Shift.AFTER
			),
			expect = 1, // Enforces that this MUST find exactly 1 match
			require = 1  // Breaks compilation/game loading if it fails to apply
	)
	private void addCustomSliderAfterLoop(CallbackInfo ci, @Local(ordinal = 2) int loopCounter) {
		Constants.LOG.info("Adding custom slider after loop");
		// MixinExtras' @Local sugar safely grabs the primitive loop counter/index
		// without you needing to guess the exact obfuscated local variable index (e.g., "l", "i").

		// SoundSource.values() excluding MASTER means the loop runs a specific number of times.
		// We check if we are on the very last iteration of the SoundSource loop.
		int totalVanillaSources = SoundSource.values().length - 1; // 9 elements
		SoundSource customSource = SoundSourceUtil.createDummySource();
		((ICustomSoundSource) (Object) customSource).setMusicDelay(true);
		Constants.LOG.info("loopCounter: " + loopCounter + " totalVanillaSources: " + totalVanillaSources + "");

		if (loopCounter == totalVanillaSources - 1) {
			// Calculate the next slot in the grid perfectly
			int nextIndex = loopCounter + 3;
			int xPos = this.width / 2 - 155 + (nextIndex % 2) * 160;
			int yPos = (this.height / 6 - 12)  + 22 * (nextIndex >> 1);
			// Add your custom slider into the native layout flow
			this.addRenderableWidget(new VolumeSlider(
					this.minecraft,
					xPos,
					yPos,
					customSource,
					150
			));
		}
	}
	*///? }
}
