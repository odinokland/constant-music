package com.odinokland.constantmusic.mixin;

import com.odinokland.constantmusic.CommonClass;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.screens.SoundOptionsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * The type Sound options screen mixin.
 */
@Mixin(SoundOptionsScreen.class)
public class SoundOptionsScreenMixin {
    /**
     * Instantiates a new Sound options screen mixin.
     */
    public SoundOptionsScreenMixin() {
        // Constants.LOG.info("Sound Options screen loaded");
    }

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
}
