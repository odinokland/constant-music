package com.odinokland.constantmusic.mixin;

//? < 1.19.3 {
import com.odinokland.constantmusic.util.ICustomSoundSource;
import net.minecraft.sounds.SoundSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SoundSource.class)
public class SoundSourceMixin implements ICustomSoundSource {

	@Unique
	private boolean isMusicDelay = false;

	@Override
	public boolean isMusicDelay() {
		return this.isMusicDelay;
	}

	@Override
	public void setMusicDelay(boolean value) {
		this.isMusicDelay = value;
	}
}
//? }
