package com.odinokland.constantmusic.platform.neoforge;

//? neoforge {
/*import com.odinokland.constantmusic.gui.ConstantMusicConfigScreen;
import net.minecraft.client.gui.screens.Screen;
//? if >=1.21 {
//import net.neoforged.fml.ModContainer;
//?} else {
import net.minecraft.client.Minecraft;
 //?}
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
public class NeoforgeConfigHelper implements IConfigScreenFactory {
	/^*
	 * Create screen screen.
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
