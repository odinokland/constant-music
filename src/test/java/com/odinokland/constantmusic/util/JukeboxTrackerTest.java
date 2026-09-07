package com.odinokland.constantmusic.util;

import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class JukeboxTrackerTest {

	@BeforeEach
	void setUp() {
		JukeboxTrackerUtility.clearJukeboxes();
	}

	@AfterEach
	void tearDown() {
		JukeboxTrackerUtility.clearJukeboxes();
	}

	@Test
	@DisplayName("Initially no jukeboxes in range should be true")
	void testInitialState() {
		assertThat(JukeboxTrackerUtility.noJukeboxesInRange()).isTrue();
		assertThat(JukeboxTrackerUtility.jukeboxes.isEmpty()).isTrue();
	}

	@Test
	@DisplayName("Starting jukebox play registers pos and stops background music")
	void testOnJukeboxPlayAndStop() {
		BlockPos pos = new BlockPos(100, 64, 100);

		// Play jukebox
		JukeboxTrackerUtility.onJukeboxPlay(null, pos);
		assertThat(JukeboxTrackerUtility.jukeboxes.containsKey(pos)).isTrue();
		assertThat(JukeboxTrackerUtility.jukeboxes.getBoolean(pos)).isTrue();
		assertThat(JukeboxTrackerUtility.noJukeboxesInRange()).isFalse();

		// Stop jukebox
		JukeboxTrackerUtility.onJukeboxStop(pos);
		assertThat(JukeboxTrackerUtility.jukeboxes.containsKey(pos)).isFalse();
		assertThat(JukeboxTrackerUtility.noJukeboxesInRange()).isTrue();
	}

	@Test
	@DisplayName("Distance calculation between coordinates")
	void testDistanceCalculation() {
		// Exactly 64 blocks on X axis -> 64^2 = 4096
		double distSqr64 = JukeboxTrackerUtility.distanceToSqr(0, 0, 0, 64, 0, 0);
		assertThat(distSqr64).isEqualTo(4096.0);
		assertThat(distSqr64).isLessThanOrEqualTo(JukeboxTrackerUtility.MAX_DISTANCE_SQR);

		// 65 blocks on X axis -> 65^2 = 4225 > 4096
		double distSqr65 = JukeboxTrackerUtility.distanceToSqr(0, 0, 0, 65, 0, 0);
		assertThat(distSqr65).isEqualTo(4225.0);
		assertThat(distSqr65).isGreaterThan(JukeboxTrackerUtility.MAX_DISTANCE_SQR);

		// 3D distance: (30, 40, 0) -> 30^2 + 40^2 = 900 + 1600 = 2500 <= 4096 (50 blocks)
		double dist3D = JukeboxTrackerUtility.distanceToSqr(10, 20, 30, 40, 60, 30);
		assertThat(dist3D).isEqualTo(2500.0);
	}

	@Test
	@DisplayName("When player is in range (<= 64 blocks), jukebox is active and background music stopped")
	void testPlayerInRange() {
		BlockPos jukeboxPos = new BlockPos(0, 64, 0);
		JukeboxTrackerUtility.onJukeboxPlay(null, jukeboxPos);

		AtomicBoolean musicStopped = new AtomicBoolean(false);

		// Player at 10 blocks away
		JukeboxTrackerUtility.updatePlayerPosition(10, 64, 0, () -> musicStopped.set(true));

		assertThat(JukeboxTrackerUtility.jukeboxes.getBoolean(jukeboxPos)).isTrue();
		assertThat(JukeboxTrackerUtility.noJukeboxesInRange()).isFalse();
		assertThat(musicStopped.get()).isTrue();
	}

	@Test
	@DisplayName("When player is out of range (> 64 blocks), jukebox is inactive and background music resumes")
	void testPlayerOutOfRange() {
		BlockPos jukeboxPos = new BlockPos(0, 64, 0);
		JukeboxTrackerUtility.onJukeboxPlay(null, jukeboxPos);

		AtomicBoolean musicStopped = new AtomicBoolean(false);

		// Player moves 70 blocks away (> 64 blocks)
		JukeboxTrackerUtility.updatePlayerPosition(70, 64, 0, () -> musicStopped.set(true));

		assertThat(JukeboxTrackerUtility.jukeboxes.getBoolean(jukeboxPos)).isFalse();
		// Because the only playing jukebox is out of range, noJukeboxesInRange returns true
		assertThat(JukeboxTrackerUtility.noJukeboxesInRange()).isTrue();
		assertThat(musicStopped.get()).isFalse();
	}

	@Test
	@DisplayName("Moving back into range re-activates jukebox suppression")
	void testPlayerMovingInAndOutOfRange() {
		BlockPos jukeboxPos = new BlockPos(0, 64, 0);
		JukeboxTrackerUtility.onJukeboxPlay(null, jukeboxPos);

		AtomicInteger stopMusicCount = new AtomicInteger(0);

		// 1. In range (10 blocks)
		JukeboxTrackerUtility.updatePlayerPosition(10, 64, 0, stopMusicCount::incrementAndGet);
		assertThat(JukeboxTrackerUtility.noJukeboxesInRange()).isFalse();
		assertThat(stopMusicCount.get()).isEqualTo(1);

		// 2. Move out of range (100 blocks)
		JukeboxTrackerUtility.updatePlayerPosition(100, 64, 0, stopMusicCount::incrementAndGet);
		assertThat(JukeboxTrackerUtility.noJukeboxesInRange()).isTrue();
		assertThat(stopMusicCount.get()).isEqualTo(1); // not incremented

		// 3. Move back in range (20 blocks)
		JukeboxTrackerUtility.updatePlayerPosition(20, 64, 0, stopMusicCount::incrementAndGet);
		assertThat(JukeboxTrackerUtility.noJukeboxesInRange()).isFalse();
		assertThat(stopMusicCount.get()).isEqualTo(2); // incremented again
	}

	@Test
	@DisplayName("Multiple jukeboxes: suppression active if at least one is in range")
	void testMultipleJukeboxes() {
		BlockPos nearJukebox = new BlockPos(10, 64, 0);
		BlockPos farJukebox = new BlockPos(200, 64, 0);

		JukeboxTrackerUtility.onJukeboxPlay(null, nearJukebox);
		JukeboxTrackerUtility.onJukeboxPlay(null, farJukebox);

		// Player at (0, 64, 0): nearJukebox is 10 blocks away, farJukebox is 200 blocks away
		JukeboxTrackerUtility.updatePlayerPosition(0, 64, 0);

		assertThat(JukeboxTrackerUtility.jukeboxes.getBoolean(nearJukebox)).isTrue();
		assertThat(JukeboxTrackerUtility.jukeboxes.getBoolean(farJukebox)).isFalse();
		assertThat(JukeboxTrackerUtility.noJukeboxesInRange()).isFalse();

		// Stop the near jukebox
		JukeboxTrackerUtility.onJukeboxStop(nearJukebox);

		// Now only farJukebox is playing, which is out of range
		assertThat(JukeboxTrackerUtility.noJukeboxesInRange()).isTrue();
	}

	@Test
	@DisplayName("Clear jukeboxes empties tracker")
	void testClearJukeboxes() {
		JukeboxTrackerUtility.onJukeboxPlay(null, new BlockPos(0, 64, 0));
		JukeboxTrackerUtility.onJukeboxPlay(null, new BlockPos(50, 64, 0));
		assertThat(JukeboxTrackerUtility.jukeboxes.size()).isEqualTo(2);

		JukeboxTrackerUtility.clearJukeboxes();
		assertThat(JukeboxTrackerUtility.jukeboxes.isEmpty()).isTrue();
		assertThat(JukeboxTrackerUtility.noJukeboxesInRange()).isTrue();
	}
}
