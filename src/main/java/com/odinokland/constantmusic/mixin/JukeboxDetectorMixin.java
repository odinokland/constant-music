package com.odinokland.constantmusic.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.odinokland.constantmusic.core.JukeboxTrackerUtility;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import net.minecraft.client.Minecraft;
//~ level_renderer
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
//~ level_import
import net.minecraft.client.multiplayer.ClientLevel;
//? if < 1.21.2 {
import org.jetbrains.annotations.Nullable;
//? }
//? if >= 1.21 {
/*import net.minecraft.world.item.JukeboxSong;
import net.minecraft.core.Holder;
*///?}
//? < 1.21 {
import net.minecraft.sounds.SoundEvent;
//? forge || neoforge {
import net.minecraft.world.item.RecordItem;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//? }
//? }

/**
 * The type Level renderer mixin.
 */
@Mixin(LevelRenderer.class)
@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
public class JukeboxDetectorMixin {
	//? if >=1.21.2 {
	/*//~ level_name
	@Shadow @Final private ClientLevel level;
	 *///?} else {
	@Shadow
	@Nullable
	private ClientLevel level;
	//?}

	@Shadow
	@Final
	private Minecraft minecraft;

	private JukeboxDetectorMixin() {
	}

	//? if >=1.21 {
	/*@WrapMethod(method = "playJukeboxSong")
	private void onJukeboxPlay(Holder<JukeboxSong> song, BlockPos pos, Operation<Void> original) {
		original.call(song, pos);
		if (level != null) {
			JukeboxTrackerUtility.onJukeboxPlay(minecraft, pos);
		}
	}

	@WrapMethod(method = "stopJukeboxSong")
	private void onJukeboxStop(BlockPos pos, Operation<Void> original) {
		original.call(pos);
		JukeboxTrackerUtility.onJukeboxStop(pos);
	}
	*///?} else {

	//? fabric {
	/*@WrapMethod(method="playStreamingMusic")
	private void onPlayStreamingMusic(@Nullable SoundEvent soundEvent, BlockPos pos, Operation<Void> original) {
		original.call(soundEvent, pos);
		if (soundEvent != null) {
			if (level != null) {
				JukeboxTrackerUtility.onJukeboxPlay(minecraft, pos);
			}
		} else {
			JukeboxTrackerUtility.onJukeboxStop(pos);
		}
	}
	*///?} else {
	//? >= 1.19.0 {
	@WrapMethod(method = "playStreamingMusic(Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/core/BlockPos;)V")
	private void onPlayStreamingMusic(@Nullable SoundEvent soundEvent, BlockPos pos, Operation<Void> original) {
		original.call(soundEvent, pos);
		if (soundEvent != null) {
			if (level != null) {
				JukeboxTrackerUtility.onJukeboxPlay(minecraft, pos);
			}
		} else {
			JukeboxTrackerUtility.onJukeboxStop(pos);
		}
	}
	//? }

	@Inject(method = "playStreamingMusic(Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/item/RecordItem;)V", at = @At("TAIL"), remap = false)
	private void onPlayStreamingMusic(SoundEvent soundEvent, BlockPos pos, RecordItem musicDiscItem, CallbackInfo ci) {
		if (soundEvent != null) {
			if (level != null) {
				JukeboxTrackerUtility.onJukeboxPlay(minecraft, pos);
			}
		} else {
			JukeboxTrackerUtility.onJukeboxStop(pos);
		}
	}
	//?}
	//?}
}
