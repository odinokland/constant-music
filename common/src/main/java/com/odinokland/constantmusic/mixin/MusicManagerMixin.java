package com.odinokland.constantmusic.mixin;

import com.odinokland.constantmusic.ConstantMusic;
import net.minecraft.client.sounds.MusicManager;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Debug(export = true)
@Mixin(MusicManager.class)
public class MusicManagerMixin {

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I", ordinal = 1))
    private int constantmusic_removeMusicDelay(int nextSongDelay, int maxDelay) {
        return Math.min(nextSongDelay, ConstantMusic.getTimer() * 20);
    }
}
