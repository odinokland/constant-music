package com.odinokland.constantmusic.gametest.platform.neoforge;

//? neoforge {
/*//? < 1.21.5 {
import com.odinokland.constantmusic.gametest.shared.ConstantMusicGameTests;
import com.odinokland.constantmusic.gametest.GametestConstants;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.GameTest;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(GametestConstants.MOD_ID)
@PrefixGameTestTemplate(false)
public class NeoforgeGameTests {
	private static final String TEMPLATE_NAME = "empty";

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

	//? fabric && >= 1.21.5 {

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
//? }
*///? }
