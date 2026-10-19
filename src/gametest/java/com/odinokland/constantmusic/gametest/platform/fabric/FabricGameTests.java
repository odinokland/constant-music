package com.odinokland.constantmusic.gametest.platform.fabric;

//? fabric {
/*import com.odinokland.constantmusic.gametest.shared.ConstantMusicGameTests;
import com.odinokland.constantmusic.gametest.GametestConstants;
import net.minecraft.gametest.framework.GameTestHelper;

//? >= 1.21.5 {
/^import net.fabricmc.fabric.api.gametest.v1.CustomTestMethodInvoker;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import java.lang.reflect.Method;
^///? } else {
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.gametest.framework.GameTest;
//? }

public class FabricGameTests /^$ gametest_implementations ^/{
	//? if <1.20.1 {
	private static final String TEMPLATE_NAME = "empty";
	//?} else {
	//private static final String TEMPLATE_NAME = GametestConstants.MOD_ID + ":" + "empty";
	//?}
	private static final String ENVIRONMENT_NAME = GametestConstants.MOD_ID + ":" + "default";
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
}
*///? }
