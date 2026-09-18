package com.odinokland.constantmusic.platform.neoforge.gametest;

//? neoforge && >= 1.21.5 {
/*import com.odinokland.constantmusic.Constants;
import com.odinokland.constantmusic.gametest.CommonGameTests;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/^*
 * Neoforge game tests.
 ^/
@EventBusSubscriber(modid = Constants.MOD_ID)
public class NeoforgeGameTests {
	private static final String STRUCTURE = "empty";
	private static final int MAX_TICKS = 400;

	/^*
	 * The default constructor.
	 ^/
	public NeoforgeGameTests() {
	}

	/^*
	 * The game tests functions.
	 ^/
	public static final DeferredRegister<Consumer<GameTestHelper>> FUNCTIONS =
			DeferredRegister.create(Registries.TEST_FUNCTION, Constants.MOD_ID);

	private static final List<String> NAMES = new ArrayList<>();

	static {
		// One shared instance: the bodies hold no per-test state.
		CommonGameTests tests = new CommonGameTests();

		add("test_delay_slider_option_and_config_screen", tests::testDelaySliderOptionAndConfigScreen);
		add("test_jukebox_music_suppression_and_resumption", tests::testJukeboxMusicSuppressionAndResumption);
		add("test_mod_loaded_and_world_entered", tests::testModLoadedAndWorldEntered);
	}

	/^*
	 * Add a game test.
	 * @param name the name of the test
	 * @param body the test body
	 ^/
	private static void add(String name, Consumer<GameTestHelper> body) {
		FUNCTIONS.register(name, () -> body);
		NAMES.add(name);
	}

	/^*
	 * Register the game tests.
	 * @param event the event
	 ^/
	@SubscribeEvent
	public static void registerTests(RegisterGameTestsEvent event) {
		Constants.LOG.info("Dan: registering tests");
		//? if >=26.1 {
		//Holder<TestEnvironmentDefinition<?>> environment = event.registerEnvironment(id("empty"));
		//? } else {
		Holder<TestEnvironmentDefinition> environment = event.registerEnvironment(id("empty"));
		//? }
		for (String name : NAMES) {
			ResourceKey<Consumer<GameTestHelper>> function = ResourceKey.create(Registries.TEST_FUNCTION, id(name));
			//? if >=26.1 {
			/^TestData<Holder<TestEnvironmentDefinition<?>>> data =
					new TestData<>(environment, id(STRUCTURE), MAX_TICKS, 0, true);
			^///? } else {
			TestData<Holder<TestEnvironmentDefinition>> data =
					new TestData<>(environment, id(STRUCTURE), MAX_TICKS, 0, true);
			//? }
			event.registerTest(id(name), new FunctionGameTestInstance(function, data));
		}
	}

	/^*
	 * Get the game test id.
	 * @param path the path
	 * @return the game test id
	 ^/
	private static ResourceLocation id(String path) {
		return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, path);
	}
}
*///? }
