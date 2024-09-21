package com.odinokland.constantmusic.mixin;

import com.odinokland.constantmusic.CommonClass;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.screens.SoundOptionsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
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
     * @param ci the callback info
     */
    @Inject(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/SoundOptionsScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;", ordinal = 4), cancellable = true)
    protected void onInit(CallbackInfo ci) {

    }
}
