package com.odinokland.constantmusic.util;

import com.odinokland.constantmusic.Constants;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

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
	 * The maximum squared distance for jukebox range (64 blocks squared = 4096).
	 */
	public static final double MAX_DISTANCE_SQR = 4096.0;

	/**
	 * On jukebox play.
	 *
	 * @param minecraft the minecraft
	 * @param pos       the pos
	 */
	public static void onJukeboxPlay(Minecraft minecraft, BlockPos pos) {
		if (minecraft != null && minecraft.getMusicManager() != null) {
			minecraft.getMusicManager().stopPlaying();
		}
		jukeboxes.put(pos, true);
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
	 * Calculate squared distance between two 3D coordinates.
	 *
	 * @param x1 x1
	 * @param y1 y1
	 * @param z1 z1
	 * @param x2 x2
	 * @param y2 y2
	 * @param z2 z2
	 * @return squared distance
	 */
	public static double distanceToSqr(double x1, double y1, double z1, double x2, double y2, double z2) {
		double dx = x1 - x2;
		double dy = y1 - y2;
		double dz = z1 - z2;
		return dx * dx + dy * dy + dz * dz;
	}

	/**
	 * Update jukebox statuses based on player position.
	 *
	 * @param playerX            player X coordinate
	 * @param playerY            player Y coordinate
	 * @param playerZ            player Z coordinate
	 * @param onJukeboxInRange   callback when at least one jukebox is in range
	 */
	public static void updatePlayerPosition(double playerX, double playerY, double playerZ, Runnable onJukeboxInRange) {
		boolean hasAnyInRange = false;
		for (Object2BooleanMap.Entry<BlockPos> jukebox : jukeboxes.object2BooleanEntrySet()) {
			BlockPos pos = jukebox.getKey();
			double distSqr = distanceToSqr(playerX, playerY, playerZ, pos.getX(), pos.getY(), pos.getZ());
			if (distSqr > MAX_DISTANCE_SQR) {
				jukebox.setValue(false);
			} else {
				jukebox.setValue(true);
				hasAnyInRange = true;
			}
		}
		if (hasAnyInRange && onJukeboxInRange != null) {
			onJukeboxInRange.run();
		}
	}

	/**
	 * Update jukebox statuses based on player position without callback.
	 *
	 * @param playerX player X coordinate
	 * @param playerY player Y coordinate
	 * @param playerZ player Z coordinate
	 */
	public static void updatePlayerPosition(double playerX, double playerY, double playerZ) {
		updatePlayerPosition(playerX, playerY, playerZ, null);
	}

	/**
	 * Check jukeboxes in range.
	 *
	 * @param client the client
	 */
	public static void checkJukeboxesInRange(Minecraft client) {
		if (client != null && client.player != null && !jukeboxes.isEmpty()) {
			updatePlayerPosition(client.player.getX(), client.player.getY(), client.player.getZ(), () -> {
				if (client.getMusicManager() != null) {
					client.getMusicManager().stopPlaying();
				}
			});
		}
	}
}
