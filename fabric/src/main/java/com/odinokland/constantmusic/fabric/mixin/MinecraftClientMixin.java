package com.odinokland.constantmusic.fabric.mixin;

import com.odinokland.constantmusic.fabric.client.event.ClientWorldEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;

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
