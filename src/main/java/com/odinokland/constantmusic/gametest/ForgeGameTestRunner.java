package com.odinokland.constantmusic.gametest;
//? >= 27.1 {
/*//? forge {

//? > 1.21.6 {
/^import com.odinokland.constantmusic.Constants;
import net.minecraftforge.gametest.GameTest;
^///? } else {
import net.minecraft.gametest.framework.GameTest;
//?}
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;


@GameTestHolder(Constants.MOD_ID)
public class ForgeGameTestRunner {
	/^*
	 * Test 1: Minecraft starts, loads the mod, and can enter the game without crashing.
	 ^/
	@GameTest
	public void testModLoadedAndWorldEntered(GameTestHelper helper) {
		GameTestRegistration.testModLoadedAndWorldEntered(helper);
	}

	/^*
	 * Test 2: When in game, if a jukebox is playing in range, the background music is stopped.
	 * If the jukebox stops playing or is out of range, the background music resumes.
	 ^/
	@GameTest
	public void testJukeboxMusicSuppressionAndResumption(GameTestHelper helper) {
		GameTestRegistration.testJukeboxMusicSuppressionAndResumption(helper);
	}

	/^*
	 * Test 3 and 4: The mod successfully configures the delay timer and formats display text.
	 ^/
	@GameTest
	public void testDelaySliderOptionAndConfigScreen(GameTestHelper helper) {
		GameTestRegistration.testDelaySliderOptionAndConfigScreen(helper);
	}
}
//? }
*///? }
