package com.odinokland.constantmusic.common.util;

import com.odinokland.constantmusic.common.Constants;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

/**
 * The type Jukebox tracker utility.
 */
public class JukeboxTrackerUtility {
	private JukeboxTrackerUtility() {}
	/**
	 * The constant jukeboxes.
	 */
	public static Object2BooleanOpenHashMap<BlockPos> jukeboxes = new Object2BooleanOpenHashMap<>();

	/**
	 * On jukebox play.
	 *
	 * @param level     the level
	 * @param minecraft the minecraft
	 * @param pos       the pos
	 */
	public static void onJukeboxPlay(Level level, Minecraft minecraft, BlockPos pos) {
		if (level != null) {
			minecraft.getMusicManager().stopPlaying();
			jukeboxes.put(pos, true);
		}
	}

	/**
	 * On jukebox stop.
	 *
	 * @param pos the pos
	 */
	public static void onJukeboxStop(BlockPos pos) {
		jukeboxes.removeBoolean(pos);
	}

	/**
	 * No jukeboxes in range boolean.
	 *
	 * @return the boolean
	 */
	public static boolean noJukeboxesInRange() {
		return jukeboxes.object2BooleanEntrySet().stream().noneMatch(Object2BooleanMap.Entry::getBooleanValue);
	}

	/**
	 * Clear jukeboxes.
	 */
	public static void clearJukeboxes() {
		jukeboxes.clear();
	}

	/**
	 * Check jukeboxes in range.
	 *
	 * @param client the client
	 */
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