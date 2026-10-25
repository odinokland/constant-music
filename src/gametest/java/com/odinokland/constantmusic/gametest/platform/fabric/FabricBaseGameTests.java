package com.odinokland.constantmusic.gametest.platform.fabric;

//? fabric&& >27 {
/*import com.odinokland.constantmusic.gametest.ConstantMusicGameTests;
import com.odinokland.constantmusic.gametest.GametestConstants;
import net.minecraft.gametest.framework.GameTestHelper;

//? >= 1.21.5 {
//import net.fabricmc.fabric.api.gametest.v1.GameTest;
//? } else {
import net.minecraft.gametest.framework.GameTest;
//? }

public class FabricBaseGameTests {
	private static final String TEMPLATE_NAME = GametestConstants.MOD_ID + ":" + "empty";
	private static final String ENVIRONMENT_NAME = GametestConstants.MOD_ID + "default";
	private static final String EMPTY_STRUCTURE = "fabric-gametest-api-v1:empty";
	/^*
	 * Test mod loaded and world entered.
	 * @param helper Minecraft Game Test Helper
	 ^/
	//$ gametest_annotation
	@GameTest(templateNamespace = GametestConstants.MOD_ID, template = TEMPLATE_NAME)
	public void testModLoadedAndWorldEntered(GameTestHelper helper) {
		ConstantMusicGameTests.testModLoadedAndWorldEntered(helper);
	}

	/^*
	 * Test delay slider option and config screen.
	 * @param helper Minecraft Game Test Helper
	 ^/
	//$ gametest_annotation
	@GameTest(templateNamespace = GametestConstants.MOD_ID, template = TEMPLATE_NAME)
	public void testDelaySliderOptionAndConfigScreen(GameTestHelper helper) {
		ConstantMusicGameTests.testDelaySliderOptionAndConfigScreen(helper);
	}

	/^*
	 * Test jukebox music suppression and resumption.
	 * @param helper Minecraft Game Test Helper
	 ^/
	//$ gametest_annotation
	@GameTest(templateNamespace = GametestConstants.MOD_ID, template = TEMPLATE_NAME)
	public void testJukeboxMusicSuppressionAndResumption(GameTestHelper helper) {
		ConstantMusicGameTests.testJukeboxMusicSuppressionAndResumption(helper);
	}
}
*///? }
