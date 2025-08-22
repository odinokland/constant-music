package com.odinokland.constantmusic.common.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.odinokland.constantmusic.common.CommonClass;
import com.odinokland.constantmusic.common.util.JukeboxTrackerUtility;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.sounds.Music;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
//? if > 1.21.3
import net.minecraft.client.sounds.MusicInfo;

/**
 * The type Music manager mixin.
 */
@Debug(export = true)
@Mixin(MusicManager.class)
public class MusicManagerMixin {

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I", ordinal = 1))
    private int constantmusic_removeMusicDelay(int nextSongDelay, int maxDelay) {
        return Math.min(nextSongDelay, CommonClass.getTimer() * 20);
    }


	//? if <= 1.21.3 {
	/*@WrapWithCondition(
		method = "tick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/sounds/MusicManager;startPlaying(Lnet/minecraft/sounds/Music;)V"
		)
	)
	private boolean dontPlayIfJukeboxInRange(MusicManager instance, Music music) {
		return JukeboxTrackerUtility.noJukeboxesInRange();
	}
	*///?}

	//? if > 1.21.3 {
    @WrapWithCondition(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/sounds/MusicManager;startPlaying(Lnet/minecraft/client/sounds/MusicInfo;)V"
            )
    )
    private boolean dontPlayIfJukeboxInRange(MusicManager instance, MusicInfo music) {
        return JukeboxTrackerUtility.noJukeboxesInRange();
    }
    //?}
}
