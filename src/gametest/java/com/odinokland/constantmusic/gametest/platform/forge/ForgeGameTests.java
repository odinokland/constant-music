package com.odinokland.constantmusic.gametest.platform.forge;

//? forge{
import com.odinokland.constantmusic.gametest.shared.ConstantMusicGameTests;
import com.odinokland.constantmusic.gametest.GametestConstants;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;
//? if < 1.20.1 {
import net.minecraftforge.gametest.PrefixGameTestTemplate;
//? } else {
//import net.minecraftforge.gametest.GameTestDontPrefix;
//? }
//? if < 1.21.5 {
import net.minecraft.gametest.framework.GameTest;
//? } else {
//import net.minecraftforge.gametest.GameTest;
//?}

//? <1.20.1 {
@PrefixGameTestTemplate(false)
//?} else if >=1.21.5 {
//@GameTestDontPrefix
//?}
@GameTestHolder(GametestConstants.MOD_ID)
public class ForgeGameTests {
	//? if <1.20.1 || neoforge {
	private static final String TEMPLATE_NAME = "empty";
	//?} else {
	//private static final String TEMPLATE_NAME = GametestConstants.MOD_ID + ":" + "empty";
	//?}
	private static final String ENVIRONMENT_NAME = GametestConstants.MOD_ID + ":" + "default";
	/**
	 * Test mod loaded and world entered.
	 * @param helper Minecraft Game Test Helper
	 */
	//$ gametest_annotation
	@GameTest(templateNamespace = GametestConstants.MOD_ID, template = TEMPLATE_NAME)
	public void testModLoadedAndWorldEntered(GameTestHelper helper) {
		ConstantMusicGameTests.testModLoadedAndWorldEntered(helper);
	}

	/**
	 * Test delay slider option and config screen.
	 * @param helper Minecraft Game Test Helper
	 */
	//$ gametest_annotation
	@GameTest(templateNamespace = GametestConstants.MOD_ID, template = TEMPLATE_NAME)
	public void testDelaySliderOptionAndConfigScreen(GameTestHelper helper) {
		ConstantMusicGameTests.testDelaySliderOptionAndConfigScreen(helper);
	}

	/**
	 * Test jukebox music suppression and resumption.
	 * @param helper Minecraft Game Test Helper
	 */
	//$ gametest_annotation
	@GameTest(templateNamespace = GametestConstants.MOD_ID, template = TEMPLATE_NAME)
	public void testJukeboxMusicSuppressionAndResumption(GameTestHelper helper) {
		ConstantMusicGameTests.testJukeboxMusicSuppressionAndResumption(helper);
	}
}
//? }
