package com.odinokland.constantmusic.mixin;

//? < 1.20 {
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import net.minecraft.client.sounds.MusicManager;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.client.resources.sounds.SoundInstance;

@Debug(export = true)
@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(MusicManager.class)
public interface MusicManagerAccessor {

	@Accessor("nextSongDelay")
	int getTimer();

	@Accessor("currentMusic")
	SoundInstance getCurrentMusic();

}
//?}
