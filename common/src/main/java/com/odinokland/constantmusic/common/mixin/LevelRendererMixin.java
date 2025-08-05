package com.odinokland.constantmusic.common.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
//import me.pajic.simple_music_control.util.JukeboxTracker;
import com.odinokland.constantmusic.common.util.JukeboxTrackerUtility;
import net.minecraft.client.Minecraft;
//? if >=1.21.6 {
import net.minecraft.client.renderer.LevelEventHandler;
import net.minecraft.world.level.Level;
//?} else {
/*import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import org.jetbrains.annotations.Nullable;
*///?}
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.item.JukeboxSong;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

//? if >=1.21.6 {
@Mixin(LevelEventHandler.class)
//?} else {
/*@Mixin(LevelRenderer.class)
*///?}
public class LevelRendererMixin {
	//? if >=1.21.6 {
	@Shadow @Final private Level level;
	//?} else {
	/*@Shadow @Nullable private ClientLevel level;
	*///?}
	@Shadow @Final private Minecraft minecraft;

	@WrapMethod(method = "playJukeboxSong")
	private void onJukeboxPlay(Holder<JukeboxSong> song, BlockPos pos, Operation<Void> original) {
		original.call(song, pos);
		JukeboxTrackerUtility.onJukeboxPlay(level, minecraft, pos);
	}

	@WrapMethod(method = "stopJukeboxSong")
	private void onJukeboxStop(BlockPos pos, Operation<Void> original) {
		original.call(pos);
		JukeboxTrackerUtility.onJukeboxStop(pos);
	}
}