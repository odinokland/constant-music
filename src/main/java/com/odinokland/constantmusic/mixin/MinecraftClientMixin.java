package com.odinokland.constantmusic.mixin;

//? if <1.21.6 && fabric {
import com.odinokland.constantmusic.platform.fabric.client.event.ClientWorldEvents;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * The type Minecraft client mixin.
 */
@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(Minecraft.class)
public abstract class MinecraftClientMixin {
	@Inject(method = "updateLevelInEngines", at = @At("TAIL"))
	private void afterClientWorldChange(ClientLevel world, CallbackInfo ci) {
		if (world != null) {
			Minecraft client = (Minecraft) (Object) this;
			ClientWorldEvents.AFTER_CLIENT_WORLD_CHANGE.invoker().afterWorldChange(client, world);
		}
	}
}
//? }
