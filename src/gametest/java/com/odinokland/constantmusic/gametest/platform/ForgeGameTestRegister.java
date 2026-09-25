package com.odinokland.constantmusic.gametest.platform;

//? forge && >= 1.21.5 {
/*import com.odinokland.constantmusic.Constants;
import com.odinokland.constantmusic.gametest.GameTestConstants;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.gametest.ForgeGameTestHooks;
import net.minecraftforge.registries.RegisterEvent;

/^*
 * Registration class for forge tests.
 ^/
@Mod.EventBusSubscriber(modid = GameTestConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeGameTestRegister {
	/^*
     * Registers the test functions.
     ^/
	@SubscribeEvent
	public static void registerTestFunctions(RegisterEvent event) {
		if (!event.getRegistryKey().equals(Registries.TEST_FUNCTION)) {
			return;
		}
		ForgeGameTestHooks.gatherTests(ForgeGameTests.class, new ForgeGameTests())
				.forEach((name, ref) -> {
					event.register(Registries.TEST_FUNCTION, name, () -> ref.consumer());
				});
	}
}
*///? }
