package com.odinokland.constantmusic.gametest.mixin;

import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import com.mojang.serialization.Lifecycle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = WorldOpenFlows.class, priority = 1001)
@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
public final class WorldOpenFlowsMixin {


	//$ world_flows_inject
	@Inject(method = "confirmWorldCreation", at = @At(value = "INVOKE_ASSIGN", target = "Lcom/mojang/serialization/Lifecycle;experimental()Lcom/mojang/serialization/Lifecycle;"), cancellable = true)
	//? if < 1.19.4 {
	private static void confirmWorldCreation(Minecraft minecraft, CreateWorldScreen screen, Lifecycle lifecycle, Runnable loadWorld, CallbackInfo ci) {
	//? } else {
	//private static void confirmWorldCreation(Minecraft minecraft, CreateWorldScreen screen, Lifecycle lifecycle, Runnable loadWorld, boolean skipWarnings, CallbackInfo ci) {
	//? }
		loadWorld.run();
		ci.cancel();
	}
}
