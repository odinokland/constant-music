package com.odinokland.constantmusic.platform.forge;

//? forge {
import com.odinokland.constantmusic.gui.ConstantMusicConfigScreen;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;

/**
 * Entry point for Forge client-side code.
 */
public class ForgeClientEntrypoint {

	/**
	 * Default constructor for ForgeClientEntrypoint.
	 */
	public ForgeClientEntrypoint() {
	}
	/**
	 * Hook to set up config screen.
	 * @param modLoadingContext The ModLoadingContext for the mod.
	 */
	public static void setupConfigScreen(ModLoadingContext modLoadingContext) {
		//? if >=1.19 {
		modLoadingContext.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () ->
				new ConfigScreenHandler.ConfigScreenFactory(
						(client, parent) -> new ConstantMusicConfigScreen(parent)
				)
		);
		//?} else if >1.16.5 <1.19 {
        /*modLoadingContext.registerExtensionPoint(ConfigGuiHandler.ConfigGuiFactory.class, () ->
				new ConfigGuiHandler.ConfigGuiFactory((client, parent) -> ModClothConfig.buildScreen(parent)
				));
        *///?} else if 1.16.5 {
		/*modLoadingContext.registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY, () ->
				(client, parent) -> ModClothConfig.buildScreen(parent)
		);
		*///?}
	}
}
//?}
