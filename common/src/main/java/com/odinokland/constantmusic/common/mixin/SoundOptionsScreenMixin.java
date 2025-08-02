package com.odinokland.constantmusic.common.mixin;

import com.odinokland.constantmusic.common.CommonClass;
import com.odinokland.constantmusic.common.Constants;
import net.minecraft.client.OptionInstance;
//? if >= 1.21 {
import net.minecraft.client.gui.screens.options.SoundOptionsScreen;
//?} else {
/*import net.minecraft.client.gui.screens.SoundOptionsScreen;
*///?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * The type Sound options screen mixin.
 */
@Mixin(SoundOptionsScreen.class)
public class SoundOptionsScreenMixin
{
	/**
	 * Instantiates a new Sound options screen mixin.
	 */
	public SoundOptionsScreenMixin() {
		Constants.LOG.info("Sound Options screen loaded");
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

		OptionInstance<Integer> seconds = CommonClass.getConfigOption();

		OptionInstance<?>[] updatedOptions = new OptionInstance<?>[defaultOptions.length + 1];

		System.arraycopy(defaultOptions, 0, updatedOptions, 0, defaultOptions.length);

		updatedOptions[updatedOptions.length - 1] = seconds;

		cir.setReturnValue(updatedOptions);
	}
	//?} else {

	/*/^*
	 * On get all options.
	 *
	 * @param ci the callback info
	 ^/
	@Inject(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/SoundOptionsScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;", ordinal = 4), cancellable = true)
	protected void onInit(CallbackInfo ci) {

	}

	*///?}
}
