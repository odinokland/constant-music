package com.odinokland.constantmusic.mixin;

import com.odinokland.constantmusic.CommonClass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractOptionSliderButton;
import net.minecraft.client.gui.components.VolumeSlider;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(VolumeSlider.class)
public class VolumeSliderMixin extends AbstractOptionSliderButton {
    @Shadow
    @Final
    private SoundSource source;

    protected VolumeSliderMixin(Minecraft minecraft, int x, int y, SoundSource source, int width) {
        super(minecraft.options, x, y, width, 20, (double) minecraft.options.getSoundSourceVolume(source));
    }

    @Inject(method = "<init>*", at = @At("RETURN"))
    private void onConstructed(CallbackInfo ci) {
        if (this.source.getName().equals("music_delay")) {
            this.setValueFromConfig();
        }
    }

    @Unique
    protected void setValueFromConfig() {
        this.value = toSliderValue((Integer) CommonClass.getTimer());
        this.applyValue();
        this.updateMessage();
    }

    @Unique
    private static int minInclusive() {
        return 0;
    }

    @Unique
    private static int maxInclusive() {
        return CommonClass.MAX_TIMER;
    }
    @Unique
    protected double toSliderValue(Integer integer) {
        return (double) Mth.map((float)integer.intValue(), (float) minInclusive(), (float) maxInclusive(), 0.0F, 1.0F);
    }

    @Unique
    protected Integer fromSliderValue(double d) {
        return Mth.floor(Mth.map(d, 0.0, 1.0, (double) minInclusive(), (double) maxInclusive()));
    }
    /**
     *
     */
    @Inject(method = "updateMessage", at = @At("HEAD"), cancellable = true)
    protected void onUpdateMessage(CallbackInfo ci) {
        if (this.source.getName().equals("music_delay")) {
            Integer value = this.fromSliderValue(this.value);
            Component component  = value.equals(0) ? Component.translatable("constantmusic.option").append(": ").append(CommonComponents.OPTION_OFF) : CommonClass.timeDisplayText(value);
            this.setMessage(component);

            ci.cancel();
        }
    }

    /**
     *
     */
    @Inject(method = "applyValue", at = @At("HEAD"), cancellable = true)
    protected void onApplyValue(CallbackInfo ci) {
        if (this.source.getName().equals("music_delay")) {
            this.options.setSoundCategoryVolume(this.source, (float)this.value);
            this.options.save();
            int value = (int) this.fromSliderValue(this.value);
            CommonClass.setTimer(value);
            ci.cancel();
        }
    }

    /**
     *
     */
    @Shadow
    protected void updateMessage() {

    }

    /**
     *
     */
    @Shadow
    protected void applyValue() {

    }
}
