package com.odinokland.constantmusic.mixin;

import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.MusicManager;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * The interface Music manager accessor.
 */
@Debug(export = true)
@Mixin(MusicManager.class)
public interface MusicManagerAccessor {

    /**
     * Gets timer.
     *
     * @return the timer
     */
    @Accessor("nextSongDelay")
    int getTimer();

    /**
     * Gets current music.
     *
     * @return the current music
     */
    @Accessor("currentMusic")
    SoundInstance getCurrentMusic();
}
