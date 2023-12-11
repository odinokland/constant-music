package com.odinokland.endlessmusic.mixin;

import com.mojang.serialization.Codec;
import com.odinokland.endlessmusic.EndlessMusicConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.SoundOptionsScreen;
import net.minecraft.sounds.SoundSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;
import java.util.Arrays;

@Mixin(SoundOptionsScreen.class)
public class SoundOptionsScreenMixin extends OptionsSubScreen {
    public SoundOptionsScreenMixin(Screen parent, Options options, Component text) {
        super(parent, options, Component.translatable("Poo"));
    }

    @Inject(method = "getAllSoundOptionsExceptMaster", at = @At("RETURN"), cancellable = true)
    protected void onGetAllOptions(CallbackInfoReturnable<OptionInstance<?>[]> cir) {
        ConfigHolder<EndlessMusicConfig> holder = AutoConfig.getConfigHolder(EndlessMusicConfig.class);
        EndlessMusicConfig config = holder.getConfig();
        OptionInstance<?>[] defaultOptions = cir.getReturnValue();

        OptionInstance<Integer> seconds = new OptionInstance("endlessmusic.option", OptionInstance.noTooltip(), (component, integer) -> {
            return integer.equals(0) ? Component.translatable("options.generic_value", new Object[]{component, CommonComponents.OPTION_OFF}) : Component.translatable("endlessmusic.option.time", new Object[]{integer});
        }, new OptionInstance.IntRange(0, 200), config.timer, (integer) -> {
            config.timer = (int) integer;
            holder.setConfig(config);
            holder.save();
        });

        OptionInstance<?>[] updatedOptions = new OptionInstance<?>[defaultOptions.length + 1];

        System.arraycopy(defaultOptions, 0, updatedOptions, 0, defaultOptions.length);

        updatedOptions[updatedOptions.length - 1] = seconds;

        cir.setReturnValue(updatedOptions);
    }
}
