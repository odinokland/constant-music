package com.odinokland.constantmusic.gametest.platform;

//? < 1.21.5 || fabric {
import com.odinokland.constantmusic.Constants;
import com.odinokland.constantmusic.gametest.ConstantMusicGameTests;
import com.odinokland.constantmusic.gametest.GametestConstants;
import net.minecraft.gametest.framework.GameTestHelper;
//? forge {
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
//? }
//? neoforge {
/*import net.minecraft.gametest.framework.GameTest;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
*///? }
//? fabric {
/*//? >= 1.21.5 {
/^import net.fabricmc.fabric.api.gametest.v1.CustomTestMethodInvoker;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import java.lang.reflect.Method;
^///? } else {
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.gametest.framework.GameTest;
//? }
*///? }

//? forge || neoforge {
//? <1.20.1 || neoforge {
@PrefixGameTestTemplate(false)
//?} else if >=1.21.5 {
//@GameTestDontPrefix
//?}
//? }
public class GameTests {
	//? if <1.20.1 || neoforge {
	private static final String TEMPLATE_NAME = "empty";
	//?} else {
	//private static final String TEMPLATE_NAME = Constants.MOD_ID + ":" + "empty";
	//?}
	private static final String ENVIRONMENT_NAME = Constants.MOD_ID + "default";
	private static final String EMPTY_STRUCTURE = "fabric-gametest-api-v1:empty";
	/**
	 * Test mod loaded and world entered.
	 * @param helper Minecraft Game Test Helper
	 */
	//$ gametest_annotation
	@GameTest(templateNamespace = Constants.MOD_ID, template = TEMPLATE_NAME)
	public void testModLoadedAndWorldEntered(GameTestHelper helper) {
		ConstantMusicGameTests.testModLoadedAndWorldEntered(helper);
	}

	/**
	 * Test delay slider option and config screen.
	 * @param helper Minecraft Game Test Helper
	 */
	//$ gametest_annotation
	@GameTest(templateNamespace = Constants.MOD_ID, template = TEMPLATE_NAME)
	public void testDelaySliderOptionAndConfigScreen(GameTestHelper helper) {
		ConstantMusicGameTests.testDelaySliderOptionAndConfigScreen(helper);
	}

	/**
	 * Test jukebox music suppression and resumption.
	 * @param helper Minecraft Game Test Helper
	 */
	//$ gametest_annotation
	@GameTest(templateNamespace = Constants.MOD_ID, template = TEMPLATE_NAME)
	public void testJukeboxMusicSuppressionAndResumption(GameTestHelper helper) {
		ConstantMusicGameTests.testJukeboxMusicSuppressionAndResumption(helper);
	}
}
//? }
