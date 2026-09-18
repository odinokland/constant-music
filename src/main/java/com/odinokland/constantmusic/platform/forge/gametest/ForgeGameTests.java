package com.odinokland.constantmusic.platform.forge.gametest;

//? forge {
import com.odinokland.constantmusic.Constants;import com.odinokland.constantmusic.gametest.CommonGameTests;
import net.minecraft.gametest.framework.GameTestHelper;
//? if < 1.20.1 {
import net.minecraftforge.gametest.PrefixGameTestTemplate;
//? }
import net.minecraftforge.gametest.GameTestHolder;
//? if < 1.21.5 {
import net.minecraft.gametest.framework.GameTest;
//? } else {
/*import net.minecraftforge.gametest.GameTest;
import net.minecraftforge.gametest.GameTestDontPrefix;
*///?}

/**
 * The game tests for the Forge platform.
 */
@GameTestHolder(Constants.MOD_ID)
//? <1.20.1 {
@PrefixGameTestTemplate(false)
//?} else if >=1.21.5 {
//@GameTestDontPrefix
//?}
public class ForgeGameTests {
	private CommonGameTests commonGameTests;
	//? if <1.20.1 {
	private static final String TEMPLATE_NAME = "empty";
	//?} else {
	//private static final String TEMPLATE_NAME = Constants.MOD_ID + ":" + "empty";
	//?}
	private static final String ENVIRONMENT_NAME = Constants.MOD_ID + "default";

	/**
	 * Default constructor for Forge game tests.
	 */
	public ForgeGameTests() {
		commonGameTests = new CommonGameTests();
	}

	/**
	 * On game test.
	 * @param helper The GameTestHelper for the game test.
	 */
	//? >=1.21.5 {
	//@GameTest
	//?} else {
	@GameTest(template = TEMPLATE_NAME)
			//?}
	public void testModLoadedAndWorldEntered(GameTestHelper helper) {
		commonGameTests.testModLoadedAndWorldEntered(helper);
	}

	/**
	 * On game test.
	 * @param helper The GameTestHelper for the game test.
	 */
	//? >=1.21.5 {
	//@GameTest
	//?} else {
	@GameTest(template = TEMPLATE_NAME)
			//?}
	public void testDelaySliderOptionAndConfigScreen(GameTestHelper helper) {
		commonGameTests.testDelaySliderOptionAndConfigScreen(helper);
	}

	/**
	 * On game test.
	 * @param helper The GameTestHelper for the game test.
	 */
	//? >=1.21.5 {
	//@GameTest
	//?} else {
	@GameTest(template = TEMPLATE_NAME)
			//?}
	public void testJukeboxMusicSuppressionAndResumption(GameTestHelper helper) {
		commonGameTests.testJukeboxMusicSuppressionAndResumption(helper);
	}
}
//?}
