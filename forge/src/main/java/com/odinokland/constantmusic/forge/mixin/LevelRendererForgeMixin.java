package com.odinokland.constantmusic.forge.mixin;

//? if < 1.21 {
/*import com.google.common.collect.Maps;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.odinokland.constantmusic.common.Constants;
import com.odinokland.constantmusic.common.util.JukeboxTrackerUtility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.RecordItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;
import java.util.Map;

@Mixin(LevelRenderer.class)
*///?}
public class LevelRendererForgeMixin {
	//? if < 1.21 {
	/*@Shadow @Nullable private ClientLevel level;
	@Shadow @Final private Minecraft minecraft;

	private final Map<BlockPos, SoundInstance> playingRecords = Maps.newHashMap();

	@WrapMethod(method="playStreamingMusic(Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/core/BlockPos;)V")
	private void onPlayStreamingMusic(@org.jetbrains.annotations.Nullable SoundEvent soundEvent, BlockPos pos, Operation<Void> original) {
		original.call(soundEvent, pos);
		if (soundEvent != null) {
			JukeboxTrackerUtility.onJukeboxPlay(level, minecraft, pos);
		} else {
			JukeboxTrackerUtility.onJukeboxStop(pos);
		}
	}

	@WrapMethod(method = "playStreamingMusic(Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/item/RecordItem;)V", remap = false)
	private void onPlayStreamingMusic(@org.jetbrains.annotations.Nullable SoundEvent soundEvent, BlockPos pos, @org.jetbrains.annotations.Nullable RecordItem musicDiscItem, Operation<Void> original) {
		original.call(soundEvent, pos, musicDiscItem);
		if (soundEvent != null) {
			JukeboxTrackerUtility.onJukeboxPlay(level, minecraft, pos);
		} else {
			JukeboxTrackerUtility.onJukeboxStop(pos);
		}
	}
	*///?}
}