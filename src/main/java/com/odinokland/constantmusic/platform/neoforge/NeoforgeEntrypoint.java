package com.odinokland.constantmusic.platform.neoforge;

//? neoforge {
/*import net.neoforged.fml.common.Mod;
import com.odinokland.constantmusic.ConstantMusic;
import com.odinokland.constantmusic.Constants;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
//? if < 1.20.6 {
import com.odinokland.constantmusic.gui.ConstantMusicConfigScreen;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.client.ConfigScreenHandler;
//?}

@Mod(Constants.MOD_ID)
public class NeoforgeEntrypoint {

	public NeoforgeEntrypoint() {
		ConstantMusic.init();
	}

	//? if < 1.20.6 {
	ModLoadingContext.get().registerExtensionPoint(
		ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory(
			(minecraft, parent) -> new ConstantMusicConfigScreen(parent)));
	//?}
}
*///?}
