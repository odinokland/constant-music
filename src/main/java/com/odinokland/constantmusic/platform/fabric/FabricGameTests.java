package com.odinokland.constantmusic.platform.fabric;

//? fabric {
/*import com.odinokland.constantmusic.ConstantMusic;
import com.odinokland.constantmusic.Constants;
import com.odinokland.constantmusic.util.JukeboxTrackerUtility;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;

//? >= 1.21.5 {
/^import net.fabricmc.fabric.api.gametest.v1.CustomTestMethodInvoker;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
^///? } else {
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.gametest.framework.GameTest;
//? }

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;

/^*
 * The type Fabric game tests.
 ^/
public class FabricGameTests /^$ gametest_implementations ^/{
	//? if <1.20.1 {
	private static final String TEMPLATE_NAME = "empty";
	//?} else {
	//private static final String TEMPLATE_NAME = Constants.MOD_ID + ":" + "empty";
	//?}
	private static final String ENVIRONMENT_NAME = Constants.MOD_ID + ":" + "default";

	/^*
	 * Test mod loaded and world entered.
	 *
	 * @param helper Minecraft Game Test Helper
	 ^/
//$ gametest_annotation
	@GameTest(templateNamespace = GametestConstants.MOD_ID, template = TEMPLATE_NAME)
	public void testModLoadedAndWorldEntered(GameTestHelper helper) {
		Constants.LOG.info("Testing Mod Loaded and World Entered");
		helper.setBlock(new BlockPos(1, 1, 1), Blocks.JUKEBOX.defaultBlockState());
		helper.assertBlockPresent(Blocks.JUKEBOX, new BlockPos(1, 1, 1));
		helper.succeed();
	}

	/^*
	 * Test delay slider option and config screen.
	 *
	 * @param helper Minecraft Game Test Helper
	 ^/
//$ gametest_annotation
	@GameTest(templateNamespace = GametestConstants.MOD_ID, template = TEMPLATE_NAME)
	public void testDelaySliderOptionAndConfigScreen(GameTestHelper helper) {
		Constants.LOG.info("Testing Jukebox Music Suppression and Resumption");
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
	 * Test jukebox music suppression and resumption.
	 *
	 * @param helper Minecraft Game Test Helper
	 ^/
//$ gametest_annotation
	@GameTest(templateNamespace = GametestConstants.MOD_ID, template = TEMPLATE_NAME)
	public void testJukeboxMusicSuppressionAndResumption(GameTestHelper helper) {
		Constants.LOG.info("Testing Delay Slider Option and Config Screen");
		ConstantMusic.resetForTesting(60);
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

	//? >= 1.21.5 {

	/^/^*
	 * Invoke test method.
	 * @param helper Game Test Helper
	 * @param method Method to invoke
	 * @throws ReflectiveOperationException If an error occurs during method invocation
	 ^/
	@Override
	public void invokeTestMethod(GameTestHelper helper, Method method) throws ReflectiveOperationException {
		method.invoke(this, helper);
	}
	^///? }

	/^*
	 * Fail the test with a position
	 *
	 * @param helper  GameTestHelper
	 * @param message Failure message
	 * @param pos     A position
	 ^/
	private static void failTest(GameTestHelper helper, String message, BlockPos pos) {
		//? >=1.21.5 {
		//helper.fail(Component.literal(message), pos);
		//?} else {
		helper.fail(message, pos);
		//?}
	}

	/^*
	 * Fail the test with an entity
	 *
	 * @param helper  GameTestHelper
	 * @param message Failure message
	 * @param entity  Entity
	 ^/
	private static void failTest(GameTestHelper helper, String message, Entity entity) {
		//? >=1.21.5 {
		//helper.fail(Component.literal(message), entity);
		//?} else {
		helper.fail(message, entity);
		//?}
	}

	/^*
	 * Assert that a condition is true
	 *
	 * @param helper    GameTestHelper
	 * @param condition Condition to assert
	 * @param message   Failure message
	 ^/
	void assertTrue(GameTestHelper helper, boolean condition, String message) {

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

	/^*
	 * An assertion method that checks if a condition is true
	 *
	 * @param helper      Minecraft game test helper
	 * @param messageType Type of message to assert
	 * @param condition   Condition to assert
	 * @param message     Failure message
	 * @throws NoSuchMethodException     When method doesn't exist on the game test helper
	 * @throws InvocationTargetException When there is an error invoking the method
	 * @throws IllegalAccessException    When there is an error accessing the method
	 ^/
	void runAssert(GameTestHelper helper, Class<?> messageType, boolean condition, String message) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		var assertMessage = (messageType == MutableComponent.class) ? Component.literal(message) : message;
		var method = GameTestHelper.class.getMethod("assertTrue", boolean.class, messageType);
		method.invoke(helper, condition, assertMessage);
	}
}
*///? }
