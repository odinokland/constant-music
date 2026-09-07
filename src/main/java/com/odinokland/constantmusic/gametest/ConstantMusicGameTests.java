package com.odinokland.constantmusic.gametest;

//? > 27.1 {
/*import com.odinokland.constantmusic.ConstantMusic;
import com.odinokland.constantmusic.Constants;
import com.odinokland.constantmusic.util.JukeboxTrackerUtility;
import net.minecraft.core.BlockPos;
//? if < 1.21.5 {
import net.minecraft.gametest.framework.GameTest;
//? }
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.Blocks;
//? forge {
	import net.minecraftforge.gametest.GameTestHolder;
	//? if >= 1.21.5 {
	//import net.minecraftforge.gametest.GameTest;
	//? }
	//? < 1.20.1 {
	import net.minecraftforge.gametest.PrefixGameTestTemplate;
	//? } else {
	//import net.minecraftforge.gametest.GameTestDontPrefix;
	//? }
//? }
//? neoforge {
/^//? if < 1.21.6 {
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
//? }
^///? }
//? fabric {
/^//? >= 1.21.5 {
/^import net.fabricmc.fabric.api.gametest.v1.CustomTestMethodInvoker;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
^///? } else {
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.gametest.framework.GameTest;
//? }
^///? }

import java.lang.reflect.InvocationTargetException;

/^*
 * Game tests verifying mod loading, config options, and jukebox music tracking.
 ^/
//? !fabric || >= 1.21.5 {
	//@GameTestHolder(Constants.MOD_ID)
	//? forge {
		//? >=1.19.3 && < 1.20.1 {
		//@PrefixGameTestTemplate(false)
		//? } else {

		//? }
	//? } else if neoforge && < 1.21.6 {
		//@PrefixGameTestTemplate(false)
	//? }
public class ConstantMusicGameTests {
//? } else {
//public class ConstantMusicGameTests implements FabricGameTest {
//? }
	//EMPTY_STRUCTURE
	//? neoforge {
	//private static final String TEMPLATE_NAME = "empty";
	//? } else if fabric && < 1.21.5 {
	//private static final String TEMPLATE_NAME = "fabric-gametest-api-v1:empty";
	//? } else {
	private static final String TEMPLATE_NAME = /^? > 1.20 { ^//^Constants.MOD_ID + ":" + ^//^? } ^/ "empty";
	//? }
	private static final String ENVIRONMENT_NAME = Constants.MOD_ID + "default";
	/^*
	 * Test 1: Minecraft starts, loads the mod, and can enter the game without crashing.
	 ^/
	//$ gametest_annotation
	@GameTest(template = TEMPLATE_NAME)
	public static void testModLoadedAndWorldEntered(GameTestHelper helper) {
		Constants.LOG.info("Testing mod loaded and world entered");
		helper.setBlock(new BlockPos(1, 1, 1), Blocks.JUKEBOX.defaultBlockState());
		helper.assertBlockPresent(Blocks.JUKEBOX, new BlockPos(1, 1, 1));
		helper.succeed();
	}

	/^*
	 * Test 2: When in game, if a jukebox is playing in range, the background music is stopped.
	 * If the jukebox stops playing or is out of range, the background music resumes.
	 ^/
	//$ gametest_annotation
	@GameTest(template = TEMPLATE_NAME)
	public static void testJukeboxMusicSuppressionAndResumption(GameTestHelper helper) {
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

	/^*
	 * Test 3 and 4: The mod successfully configures the delay timer and formats display text.
	 ^/
	//$ gametest_annotation
	@GameTest(template = TEMPLATE_NAME)
	public static void testDelaySliderOptionAndConfigScreen(GameTestHelper helper) {
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

	/^*
	 * Fail the test with a position
	 * @param helper GameTestHelper
	 * @param message Failure message
	 * @param pos A position
	 ^/
	private static void failTest(GameTestHelper helper, String message, BlockPos pos) {
		//? >= 1.21.5 {
		//helper.fail(Component.literal(message), pos);
		//? } else {
		helper.fail(message, pos);
		//? }
	}

	/^*
	 * Fail the test with an entity
	 * @param helper GameTestHelper
	 * @param message Failure message
	 * @param entity Entity
	 ^/
	private static void failTest(GameTestHelper helper, String message, Entity entity) {
		//? >= 1.21.5 {
		//helper.fail(Component.literal(message), entity);
		//? } else {
		helper.fail(message, entity);
		//? }
	}

	/^*
	 * Assert that a condition is true
	 * @param helper GameTestHelper
	 * @param condition Condition to assert
	 * @param message Failure message
	 ^/
	static void assertTrue(GameTestHelper helper, boolean condition, String message) {

		//? if >=1.21.5 {
		/^try {
			runAssert(helper, MutableComponent.class, condition, message);
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			helper.fail(Component.literal("Failed to run assertTrue"));
		}
		^///?} else {
		try {
			runAssert(helper, String.class, condition, message);
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			helper.fail("Failed to run assertTrue");
		}
		//?}
		//"Failed to run assertTrue"
	}

	static void runAssert(GameTestHelper helper, Class<?> messageType, boolean condition, String message) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		var assertMessage = (messageType == MutableComponent.class) ? Component.literal(message) : message;
		var method = GameTestHelper.class.getMethod("assertTrue", boolean.class, messageType);
		method.invoke(helper, condition, assertMessage);
	}
}
*///? }
