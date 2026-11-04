package com.odinokland.constantmusic.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.odinokland.constantmusic.core.ConstantMusicManager;
import com.odinokland.constantmusic.core.JukeboxTrackerUtility;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.util.RandomSource;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.sounds.Music;
//? if <= 1.21.3 || > 1.21.8 {
import net.minecraft.sounds.Music;
//? } else {
//import net.minecraft.client.sounds.MusicInfo;
//? }

/**
 * The type Music manager mixin.
 */
@Debug(export = true)
@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(MusicManager.class)
public class MusicManagerMixin {
	@Shadow @Final
	private Minecraft minecraft;
	@Shadow @Final
	private RandomSource random;

	private MusicManagerMixin() {
	}

	/**
	 *
	 * @return
	 */
	@Unique
	private Music constant_music$getSituationalMusic() {
		//? > 1.21.3 && < 1.21.11 {
		//return minecraft.getSituationalMusic().music();
		//? } else {
		return minecraft.getSituationalMusic();
		//? }
	}

	@Redirect(method = "tick", at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I", ordinal = 1))
	private int constantmusic_removeMusicDelay(int nextSongDelay, int maxDelay) {
		Music music = this.constant_music$getSituationalMusic();
		return Math.min(nextSongDelay, ConstantMusicManager.SINGLETON.getFrequency().getNextSongDelay(music, this.random));
	}


	//? if <=1.21.3 || >1.21.8 {
	@WrapWithCondition(
			method = "tick",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/sounds/MusicManager;startPlaying(Lnet/minecraft/sounds/Music;)V"
			)
	)
	private boolean dontPlayIfJukeboxInRange(MusicManager instance, Music music) {
		return JukeboxTrackerUtility.noJukeboxesInRange();
	}
	//?} else {
    /*@WrapWithCondition(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/sounds/MusicManager;startPlaying(Lnet/minecraft/client/sounds/MusicInfo;)V"
            )
    )
    private boolean dontPlayIfJukeboxInRange(MusicManager instance, MusicInfo music) {
        return JukeboxTrackerUtility.noJukeboxesInRange();
    }
    *///?}
}
