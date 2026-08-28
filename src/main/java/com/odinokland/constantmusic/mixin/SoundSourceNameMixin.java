package com.odinokland.constantmusic.mixin;

//? < 1.19.3 {
/*import com.odinokland.constantmusic.util.ICustomSoundSource;
import net.minecraft.sounds.SoundSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SoundSource.class)
public class SoundSourceNameMixin {
	@Inject(method = "getName", at = @At("HEAD"), cancellable = true)
	private void overrideNameForCustomSource(CallbackInfoReturnable<String> cir) {
		// Check if this specific enum instance was flagged by our plugin
		if (((ICustomSoundSource) this).isMusicDelay()) {
			cir.setReturnValue("music_delay"); // Bypasses original name ("master")
		}
	}
}
*///? }
