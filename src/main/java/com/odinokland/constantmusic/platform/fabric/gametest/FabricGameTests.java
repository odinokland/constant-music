package com.odinokland.constantmusic.platform.fabric.gametest;

//? fabric {
/*import com.odinokland.constantmusic.gametest.CommonGameTests;
import net.minecraft.gametest.framework.GameTestHelper;
//? >= 1.21.5 {
/^import net.fabricmc.fabric.api.gametest.v1.CustomTestMethodInvoker;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import java.lang.reflect.Method;
^///? } else {
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.gametest.framework.GameTest;
//? }
//? >= 1.21.5 {
//public class FabricGameTests implements CustomTestMethodInvoker {
//? } else {
public class FabricGameTests implements FabricGameTest {
//? }
	private final CommonGameTests commonGameTests;
	//? if < 1.21.5 {
	private static final String TEMPLATE_NAME = "fabric-gametest-api-v1:empty";
	//? } else {
	//private static final String TEMPLATE_NAME = "constantmusic:empty";
	//? }

	public FabricGameTests() {
		commonGameTests = new CommonGameTests();
	}

	//$ gametest_annotation
	@GameTest(template = TEMPLATE_NAME)
	public void testModLoadedAndWorldEntered(GameTestHelper helper) {
		commonGameTests.testModLoadedAndWorldEntered(helper);
	}

	//$ gametest_annotation
	@GameTest(template = TEMPLATE_NAME)
	public void testDelaySliderOptionAndConfigScreen(GameTestHelper helper) {
		commonGameTests.testDelaySliderOptionAndConfigScreen(helper);
	}

	//$ gametest_annotation
	@GameTest(template = TEMPLATE_NAME)
	public void testJukeboxMusicSuppressionAndResumption(GameTestHelper helper) {
		commonGameTests.testJukeboxMusicSuppressionAndResumption(helper);
	}

	//? >= 1.21.5 {
	/^@Override
	public void invokeTestMethod(GameTestHelper helper, Method method) throws ReflectiveOperationException {
		method.invoke(this, helper);
	}
	^///? }
}
*///? }
