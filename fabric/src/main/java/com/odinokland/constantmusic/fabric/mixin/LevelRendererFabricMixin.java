package com.odinokland.constantmusic.fabric.mixin;

import com.google.common.collect.Maps;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.odinokland.constantmusic.common.Constants;
import com.odinokland.constantmusic.common.util.JukeboxTrackerUtility;
import net.minecraft.client.Minecraft;
//? if >=1.21.6 {
import net.minecraft.client.renderer.LevelEventHandler;
//?} else {
/*import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.Nullable;
*///?}
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

/**
 * The type Level renderer fabric mixin.
 */
//? if >=1.21.6 {
@Mixin(LevelEventHandler.class)
 //?} else {
/*@Mixin(LevelRenderer.class)
*///?}
public class LevelRendererFabricMixin
{
	//? if <1.21.6 {
	/*@Shadow @Nullable private ClientLevel level;
	*///?}
	//? if < 1.21 {
	/*private final Map<BlockPos, SoundInstance> playingRecords = Maps.newHashMap();
	*///?}
	@Shadow @Final private Minecraft minecraft;

	//? if <1.21 {
	/*@WrapMethod(method="playStreamingMusic")
	private void onPlayStreamingMusic(@Nullable SoundEvent soundEvent, BlockPos pos, Operation<Void> original) {
		original.call(soundEvent, pos);
		if (soundEvent != null) {
			JukeboxTrackerUtility.onJukeboxPlay(level, minecraft, pos);
		} else {
			JukeboxTrackerUtility.onJukeboxStop(pos);
		}
	}
	*///?}
}
