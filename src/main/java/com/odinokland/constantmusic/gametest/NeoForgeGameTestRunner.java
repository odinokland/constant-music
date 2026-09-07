package com.odinokland.constantmusic.gametest;
//? >= 27.1 {
/*//? neoforge {
/^//? > 1.21.6 {
//
//? } else {
import net.neoforged.neoforge.gametest.GameTest;
//? }
import net.minecraft.gametest.framework.GameTestHelper;

public class NeoForgeGameTestRunner {
	/^*
	 * Test 1: Minecraft starts, loads the mod, and can enter the game without crashing.
	 ^/
	//~ if > 1.21.6 '@GameTest' -> '//@GameTest'
	@GameTest
	public static void testModLoadedAndWorldEntered(GameTestHelper helper) {
		GameTestRegistration.testModLoadedAndWorldEntered(helper);
	}

	/^*
	 * Test 2: When in game, if a jukebox is playing in range, the background music is stopped.
	 * If the jukebox stops playing or is out of range, the background music resumes.
	 ^/
	//~ if > 1.21.6 '@GameTest' -> '//@GameTest'
	@GameTest
	public static void testJukeboxMusicSuppressionAndResumption(GameTestHelper helper) {
		GameTestRegistration.testJukeboxMusicSuppressionAndResumption(helper);
	}

	/^*
	 * Test 3 and 4: The mod successfully configures the delay timer and formats display text.
	 ^/
	//~ if > 1.21.6 '@GameTest' -> '//@GameTest'
	@GameTest
	public static void testDelaySliderOptionAndConfigScreen(GameTestHelper helper) {
		GameTestRegistration.testDelaySliderOptionAndConfigScreen(helper);
	}
}

^///? }
*///? }
