package com.odinokland.constantmusic.mixin;

import com.odinokland.constantmusic.ConstantMusic;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.SoundOptionsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SoundOptionsScreen.class)
public class SoundOptionsScreenMixin extends OptionsSubScreen {
    public SoundOptionsScreenMixin(Screen parent, Options options, Component text) {
        super(parent, options, text);
    }

    @Inject(method = "getAllSoundOptionsExceptMaster", at = @At("RETURN"), cancellable = true)
    protected void onGetAllOptions(CallbackInfoReturnable<OptionInstance<?>[]> cir) {
        OptionInstance<?>[] defaultOptions = cir.getReturnValue();

        OptionInstance<Integer> seconds = new OptionInstance<Integer>("constantmusic.option", OptionInstance.noTooltip(), (component, integer) -> {
            return integer.equals(0) ? Component.translatable("options.generic_value", new Object[]{component, CommonComponents.OPTION_OFF}) : Component.translatable("constantmusic.option.time", new Object[]{integer});
        }, new OptionInstance.IntRange(0, 200), ConstantMusic.getTimer(), (integer) -> {
            ConstantMusic.setTimer(Integer.parseInt(integer.toString()));
        });

        OptionInstance<?>[] updatedOptions = new OptionInstance<?>[defaultOptions.length + 1];

        System.arraycopy(defaultOptions, 0, updatedOptions, 0, defaultOptions.length);

        updatedOptions[updatedOptions.length - 1] = seconds;

        cir.setReturnValue(updatedOptions);
    }
}
