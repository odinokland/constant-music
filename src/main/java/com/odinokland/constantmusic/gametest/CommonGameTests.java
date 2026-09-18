package com.odinokland.constantmusic.gametest;

import com.odinokland.constantmusic.ConstantMusic;
import com.odinokland.constantmusic.Constants;
import com.odinokland.constantmusic.util.JukeboxTrackerUtility;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.Blocks;
import java.lang.reflect.InvocationTargetException;
//? neoforge_game_annotations {
/*import net.minecraft.gametest.framework.GameTest;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
*///? }
/**
 * Game tests verifying mod loading, config options, and jukebox music tracking.
 */
//? neoforge_game_annotations {
/*@GameTestHolder(Constants.MOD_ID)
@PrefixGameTestTemplate(false)
*///?}
public class CommonGameTests {
	/**
	 * Test 1: Minecraft starts, loads the mod, and can enter the game without crashing.
	 */
	//? neoforge_game_annotations
	//@GameTest(template = "empty")
	public void testModLoadedAndWorldEntered(GameTestHelper helper) {
		Constants.LOG.info("Testing mod loaded and world entered");
		helper.setBlock(new BlockPos(1, 1, 1), Blocks.JUKEBOX.defaultBlockState());
		helper.assertBlockPresent(Blocks.JUKEBOX, new BlockPos(1, 1, 1));
		helper.succeed();
	}

	/**
	 * Test 2: When in game, if a jukebox is playing in range, the background music is stopped.
	 * If the jukebox stops playing or is out of range, the background music resumes.
	 */
	//? neoforge_game_annotations
	//@GameTest(template = "empty")
	public void testJukeboxMusicSuppressionAndResumption(GameTestHelper helper) {
		Constants.LOG.info("Testing jukebox music suppression and resumption");
		BlockPos localPos = new BlockPos(1, 1, 1);
		helper.setBlock(localPos, Blocks.JUKEBOX.defaultBlockState());
		BlockPos absolutePos = helper.absolutePos(localPos);

		// Clean state
		JukeboxTrackerUtility.clearJukeboxes();
		if (!JukeboxTrackerUtility.noJukeboxesInRange()) {
			failTest(helper, "Expected no jukeboxes in range initially", localPos);
			return;
		}

		// 1. Jukebox starts playing in range
		JukeboxTrackerUtility.onJukeboxPlay(null, absolutePos);
		if (JukeboxTrackerUtility.noJukeboxesInRange()) {
			failTest(helper, "Expected jukebox to suppress music when playing", localPos);
			return;
		}

		// 2. Player is close (in range <= 64 blocks)
		JukeboxTrackerUtility.updatePlayerPosition(absolutePos.getX(), absolutePos.getY(), absolutePos.getZ());
		if (JukeboxTrackerUtility.noJukeboxesInRange()) {
			failTest(helper, "Expected music to remain stopped while player is in range", localPos);
			return;
		}

		// 3. Player moves out of range (> 64 blocks, e.g. 100 blocks away)
		JukeboxTrackerUtility.updatePlayerPosition(absolutePos.getX() + 100, absolutePos.getY(), absolutePos.getZ());
		if (!JukeboxTrackerUtility.noJukeboxesInRange()) {
			failTest(helper, "Expected music to resume when player moves out of range", localPos);
			return;
		}

		// 4. Player moves back into range
		JukeboxTrackerUtility.updatePlayerPosition(absolutePos.getX(), absolutePos.getY(), absolutePos.getZ());
		if (JukeboxTrackerUtility.noJukeboxesInRange()) {
			failTest(helper, "Expected music to stop again when player re-enters range", localPos);
			return;
		}

		// 5. Jukebox stops playing
		JukeboxTrackerUtility.onJukeboxStop(absolutePos);
		if (!JukeboxTrackerUtility.noJukeboxesInRange()) {
			failTest(helper, "Expected music to resume when jukebox stops playing", localPos);
			return;
		}

		helper.succeed();
	}

	/**
	 * Test 3 and 4: The mod successfully configures the delay timer and formats display text.
	 */
	//? neoforge_game_annotations
	//@GameTest(template = "empty")
	public void testDelaySliderOptionAndConfigScreen(GameTestHelper helper) {
		ConstantMusic.resetForTesting(60);
		Constants.LOG.info("Testing delay slider option and config screen");
		if (ConstantMusic.getTimer() != 60) {
			failTest(helper, "Config timer value mismatch", new BlockPos(1, 1, 1));
			return;
		}

		ConstantMusic.setTimer(120);
		if (ConstantMusic.getTimer() != 120) {
			failTest(helper, "Config timer update mismatch", new BlockPos(1, 1, 1));
			return;
		}

		var text = ConstantMusic.timeDisplayText(60);
		if (text == null) {
			failTest(helper, "Time display text should not be null", new BlockPos(1, 1, 1));
			return;
		}

		helper.succeed();
	}

	/**
	 * Fail the test with a position
	 *
	 * @param helper  GameTestHelper
	 * @param message Failure message
	 * @param pos     A position
	 */
	private void failTest(GameTestHelper helper, String message, BlockPos pos) {
		//? >=1.21.5 {
		//helper.fail(Component.literal(message), pos);
		//?} else {
		helper.fail(message, pos);
		//?}
	}

	/**
	 * Fail the test with an entity
	 *
	 * @param helper  GameTestHelper
	 * @param message Failure message
	 * @param entity  Entity
	 */
	private void failTest(GameTestHelper helper, String message, Entity entity) {
		//? >=1.21.5 {
		//helper.fail(Component.literal(message), entity);
		//?} else {
		helper.fail(message, entity);
		//?}
	}

	/**
	 * Assert that a condition is true
	 *
	 * @param helper    GameTestHelper
	 * @param condition Condition to assert
	 * @param message   Failure message
	 */
	void assertTrue(GameTestHelper helper, boolean condition, String message) {

		//? if >=1.21.5 {
		/*try {
			runAssert(helper, MutableComponent.class, condition, message);
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			helper.fail(Component.literal("Failed to run assertTrue"));
		}
		*///?} else {
		try {
			runAssert(helper, String.class, condition, message);
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			helper.fail("Failed to run assertTrue");
		}
		//?}
		//"Failed to run assertTrue"
	}

	void runAssert(GameTestHelper helper, Class<?> messageType, boolean condition, String message) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		var assertMessage = (messageType == MutableComponent.class) ? Component.literal(message) : message;
		var method = GameTestHelper.class.getMethod("assertTrue", boolean.class, messageType);
		method.invoke(helper, condition, assertMessage);
	}
}
