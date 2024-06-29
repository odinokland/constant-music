package com.odinokland.constantmusic.mixin;

import com.odinokland.constantmusic.CommonClass;
import com.odinokland.constantmusic.Constants;
import net.minecraft.client.sounds.MusicManager;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * The type Music manager mixin.
 */
@Debug(export = true)
@Mixin(MusicManager.class)
public class MusicManagerMixin {

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I", ordinal = 1))
    private int constantmusic_removeMusicDelay(int nextSongDelay, int maxDelay) {
        Constants.LOG.info("REMOVING TIIIIIIICK");
        return Math.min(nextSongDelay, CommonClass.getTimer() * 20);
    }
}
