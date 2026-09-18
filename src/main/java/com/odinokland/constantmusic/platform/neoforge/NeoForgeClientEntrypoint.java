package com.odinokland.constantmusic.platform.neoforge;

//? neoforge {
/*import com.odinokland.constantmusic.gui.ConstantMusicConfigScreen;
import net.neoforged.fml.ModContainer;
//? if >= 1.20.6 {
//import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
//? } else {
import net.neoforged.neoforge.client.ConfigScreenHandler;
//? }

import java.util.function.Supplier;

/^*
 * NeoForge client entrypoint.
 ^/
public class NeoForgeClientEntrypoint {

	/^*
	 * Default constructor.
	 ^/
	public NeoForgeClientEntrypoint() {
	}
	/^*
	 * Setup config screen.
	 * @param modContainer The mod container.
	 ^/
	public static void setupConfigScreen(ModContainer modContainer) {
		//? if >= 1.20.6 {
		//modContainer.registerExtensionPoint(IConfigScreenFactory.class, (Supplier<IConfigScreenFactory>) NeoforgeConfigHelper::new);
		//? } else {
		modContainer.registerExtensionPoint(
				ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory(
						(minecraft, parent) -> new ConstantMusicConfigScreen(parent)));
		//?}
	}
}
*///? }
