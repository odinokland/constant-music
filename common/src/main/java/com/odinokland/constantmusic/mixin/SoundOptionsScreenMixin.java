package com.odinokland.constantmusic.mixin;

import com.odinokland.constantmusic.CommonClass;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.screens.options.SoundOptionsScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SoundOptionsScreen.class)
public class SoundOptionsScreenMixin {
//    public SoundOptionsScreenMixin(Screen parent, Options options, Component text) {
//        super(parent, options, text);
//    }

    @Inject(method = "getAllSoundOptionsExceptMaster", at = @At("RETURN"), cancellable = true)
    protected void onGetAllOptions(CallbackInfoReturnable<OptionInstance<?>[]> cir) {
        OptionInstance<?>[] defaultOptions = cir.getReturnValue();

        OptionInstance<Integer> seconds = new OptionInstance<Integer>("constantmusic.option", OptionInstance.noTooltip(), (component, integer) -> {
            return integer.equals(0) ? Component.translatable("options.generic_value", new Object[]{component, CommonComponents.OPTION_OFF}) : this.constant_music$timeDisplayText(integer);
        }, new OptionInstance.IntRange(0, 600), CommonClass.getTimer(), (integer) -> {
            CommonClass.setTimer(Integer.parseInt(integer.toString()));
        });

        OptionInstance<?>[] updatedOptions = new OptionInstance<?>[defaultOptions.length + 1];

        System.arraycopy(defaultOptions, 0, updatedOptions, 0, defaultOptions.length);

        updatedOptions[updatedOptions.length - 1] = seconds;

        cir.setReturnValue(updatedOptions);
    }

    @Unique
    private MutableComponent constant_music$timeDisplayText(Integer seconds) {
        int minutes = seconds/60;
        int remainingSeconds = seconds%60;
        if (minutes > 0) {
            return Component.translatable("constantmusic.option.minutes_and_seconds", new Object[]{minutes, remainingSeconds});
        }
        return Component.translatable("constantmusic.option.seconds", new Object[]{seconds});

    }
}
