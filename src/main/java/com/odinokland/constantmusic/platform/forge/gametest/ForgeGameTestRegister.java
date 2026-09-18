package com.odinokland.constantmusic.platform.forge.gametest;

//? forge && >= 1.21.5 {
/*import com.odinokland.constantmusic.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.gametest.ForgeGameTestHooks;
import net.minecraftforge.registries.RegisterEvent;

/^*
 * Registration class for forge tests.
 ^/
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
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
					Constants.LOG.info("Dan: Registering test function: {}", name);
					event.register(Registries.TEST_FUNCTION, name, () -> ref.consumer());
				});
	}
}
*///? }
