package com.odinokland.constantmusic.platform.neoforge;

//? neoforge {
/*import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import com.odinokland.constantmusic.ConstantMusic;
import com.odinokland.constantmusic.Constants;
//? if >= 1.20.6 {
/^import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import java.util.function.Supplier;
^///? }
//? if < 1.20.6 {
import com.odinokland.constantmusic.gui.ConstantMusicConfigScreen;
import net.neoforged.neoforge.client.ConfigScreenHandler;
//?}

/^*
 * The type Neoforge entrypoint.
 ^/
@Mod(Constants.MOD_ID)
public class NeoforgeEntrypoint {

	/^*
	 * Instantiates a new Neoforge entrypoint.
	 ^/
	public NeoforgeEntrypoint(ModContainer modContainer) {
		ConstantMusic.init();
		//? if >= 1.20.6 {
		//modContainer.registerExtensionPoint(IConfigScreenFactory.class, (Supplier<IConfigScreenFactory>) NeoforgeConfigHelper::new);
		//? } else {
		modContainer.registerExtensionPoint(
				ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory(
						(minecraft, parent) -> new ConstantMusicConfigScreen(parent)));
		//?}
	}
}
*///?}
