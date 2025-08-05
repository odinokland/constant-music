package com.odinokland.constantmusic.common.util;

import com.odinokland.constantmusic.common.Constants;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class JukeboxTrackerUtility {
	public static Object2BooleanOpenHashMap<BlockPos> jukeboxes = new Object2BooleanOpenHashMap<>();

	public static void onJukeboxPlay(Level level, Minecraft minecraft, BlockPos pos) {
		if (level != null) {
			minecraft.getMusicManager().stopPlaying();
			jukeboxes.put(pos, true);
		}
	}

	public static void onJukeboxStop(BlockPos pos) {
		jukeboxes.removeBoolean(pos);
	}

	public static boolean noJukeboxesInRange() {
		return jukeboxes.object2BooleanEntrySet().stream().noneMatch(Object2BooleanMap.Entry::getBooleanValue);
	}

	public static void clearJukeboxes() {
		jukeboxes.clear();
	}

	public static void checkJukeboxesInRange(Minecraft client) {
		if (client.player != null &&  !jukeboxes.isEmpty()) {
			// int hasJukeboxInRange = 0;
			// int hasNoJukeboxInRange = 0;
			for (Object2BooleanMap.Entry<BlockPos> jukebox : jukeboxes.object2BooleanEntrySet()) {
				BlockPos pos = jukebox.getKey();
				if (client.player.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) > 4096) {
					jukeboxes.put(pos, false);
					//hasNoJukeboxInRange++;
				} else {
					jukeboxes.put(pos, true);
					//hasJukeboxInRange++;
					client.getMusicManager().stopPlaying();
				}
			}
			//Constants.LOG.info("Jukeboxes in range: " + hasJukeboxInRange + ", no jukeboxes in range: " + hasNoJukeboxInRange);
		}
	}
}