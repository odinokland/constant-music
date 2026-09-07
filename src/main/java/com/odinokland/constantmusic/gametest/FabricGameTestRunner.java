package com.odinokland.constantmusic.gametest;
//? > 27.1 {
/*//? fabric {
/^//? >= 1.21.5 {
/^import net.fabricmc.fabric.api.gametest.v1.CustomTestMethodInvoker;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
^///? } else {
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.gametest.framework.GameTest;
//? }
import net.minecraft.gametest.framework.GameTestHelper;

import java.lang.reflect.Method;

public class FabricGameTestRunner
//? >= 1.21.5 {
		//implements CustomTestMethodInvoker {
//? } else {
implements FabricGameTest {
//? }
	//? >= 1.21.5 {
	/^@Override
	public void invokeTestMethod(GameTestHelper helper, Method method) throws ReflectiveOperationException {
		method.invoke(this, helper);
	}
	^///? }
	private static final String TEMPLATE_NAME = /^? if forge || neoforge { ^/"empty_gametest"/^? } else { ^//^"fabric-gametest-api-v1:empty"^//^?}^/;

	/^*
	 * Test 1: Minecraft starts, loads the mod, and can enter the game without crashing.
	 ^/
	@GameTest
	public void testModLoadedAndWorldEntered(GameTestHelper helper) {
		ConstantMusicGameTests.testModLoadedAndWorldEntered(helper);
	}

	/^*
	 * Test 2: When in game, if a jukebox is playing in range, the background music is stopped.
	 * If the jukebox stops playing or is out of range, the background music resumes.
	 ^/
	@GameTest
	public void testJukeboxMusicSuppressionAndResumption(GameTestHelper helper) {
		ConstantMusicGameTests.testJukeboxMusicSuppressionAndResumption(helper);
	}

	/^*
	 * Test 3 and 4: The mod successfully configures the delay timer and formats display text.
	 ^/
	@GameTest
	public void testDelaySliderOptionAndConfigScreen(GameTestHelper helper) {
		ConstantMusicGameTests.testDelaySliderOptionAndConfigScreen(helper);
	}
}
^///? }
*///? }
