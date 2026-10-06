package com.odinokland.constantmusic.mixin;

import com.mojang.serialization.Lifecycle;
import com.odinokland.constantmusic.ConstantMusic;
import com.odinokland.constantmusic.Constants;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * WorldOpenFlows mixin class
 */
@Mixin(value = WorldOpenFlows.class, priority = 1001)
@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
public final class WorldOpenFlowsMixin {

	/**
	 * Default constructor
	 */
	public WorldOpenFlowsMixin() {

	}

	//? if < 1.19.4 {
	/**
	 * Hook into confirmWorldCreation to bypass experimental settings warning
	 * @param minecraft The minecraft instance
	 * @param screen The create world screen
	 * @param lifecycle	Lifecycle class
	 * @param loadWorld the class to load the world
	 * @param ci callback info
	 */
	//$ world_flows_inject
	@Inject(method = "confirmWorldCreation", at = @At(value = "INVOKE_ASSIGN", target = "Lcom/mojang/serialization/Lifecycle;experimental()Lcom/mojang/serialization/Lifecycle;"), cancellable = true)
	private static void confirmWorldCreation(Minecraft minecraft, CreateWorldScreen screen, Lifecycle lifecycle, Runnable loadWorld, CallbackInfo ci) {
		if (ConstantMusic.isModLoaded(Constants.MOD_ID+"_gametest")) {
			constant_music$bypassExperimentalScreen(loadWorld, ci);
		}
	}
	//? } else {
	/*/^*
	 * Hook into confirmWorldCreation to bypass experimental settings warning
	 * @param minecraft The minecraft instance
	 * @param screen The create world screen
	 * @param lifecycle	Lifecycle class
	 * @param loadWorld the class to load the world
	 * @param skipWarnings whether to skip warnings
	 * @param ci callback info
	 ^/
	//$ world_flows_inject
	@Inject(method = "confirmWorldCreation", at = @At(value = "INVOKE_ASSIGN", target = "Lcom/mojang/serialization/Lifecycle;experimental()Lcom/mojang/serialization/Lifecycle;"), cancellable = true)
	private static void confirmWorldCreation(Minecraft minecraft, CreateWorldScreen screen, Lifecycle lifecycle, Runnable loadWorld, boolean skipWarnings, CallbackInfo ci) {
		if (ConstantMusic.isModLoaded(Constants.MOD_ID+"_gametest")) {
			constant_music$bypassExperimentalScreen(loadWorld, ci);
		}
	}
	*///? }

	/**
	 * Logic to skip experimental settings warning
	 * @param loadWorld the class to load the world
	 * @param ci callback info
	 */
	@Unique
	private static void constant_music$bypassExperimentalScreen(Runnable loadWorld, CallbackInfo ci) {
		loadWorld.run();
		ci.cancel();
	}
}
