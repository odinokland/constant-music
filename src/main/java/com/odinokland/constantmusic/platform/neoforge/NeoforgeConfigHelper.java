package com.odinokland.constantmusic.platform.neoforge;

//? neoforge && >=1.20.6 {
/*import com.odinokland.constantmusic.gui.ConstantMusicConfigScreen;
import net.minecraft.client.gui.screens.Screen;
//? if >=1.21 {
//import net.neoforged.fml.ModContainer;
//?} else {
import net.minecraft.client.Minecraft;
 //?}
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
/^*
 * The type Neoforge config helper.
 ^/
public class NeoforgeConfigHelper implements IConfigScreenFactory {
	/^*
	 * Instantiates a new Client config helper.
	 ^/
	public NeoforgeConfigHelper() {}

	/^*
	 * Create screen.
	 *
	 * @param container     the container
	 * @param modListScreen the mod list screen
	 * @return the screen
	 ^/
	@Override
	public Screen createScreen(/^? >=1.21 {^/ /^ModContainer ^//^?} else {^/ Minecraft /^?}^/ container, Screen modListScreen) {
		return new ConstantMusicConfigScreen(modListScreen);
	}
}
*///? }
