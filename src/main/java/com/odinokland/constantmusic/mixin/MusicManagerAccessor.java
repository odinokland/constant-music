package com.odinokland.constantmusic.mixin;

//? < 1.20 {
import com.odinokland.constantmusic.Constants;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import net.minecraft.client.sounds.MusicManager;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.client.resources.sounds.SoundInstance;

/**
 * Mixin for {@link MusicManager}.
 */
@Debug(export = true)
@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(value = MusicManager.class, remap = Constants.REMAP_MIXINS)
public interface MusicManagerAccessor {

	/**
	 * Timer for next song.
	 * @return the timer for next song
	 */
	@Accessor("nextSongDelay")
	int getTimer();

	/**
	 * Current music.
	 * @return the current music
	 */
	@Accessor("currentMusic")
	SoundInstance getCurrentMusic();

}
//?}
