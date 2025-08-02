package com.odinokland.constantmusic.neoforge;

import com.odinokland.constantmusic.common.CommonClass;
import com.odinokland.constantmusic.common.Constants;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
//? if < 1.20.6 {
import com.odinokland.constantmusic.common.gui.ConstantMusicConfigScreen;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.client.ConfigScreenHandler;
//?}

@Mod(Constants.MOD_ID)
public class ConstantMusicNeoForge {

	public ConstantMusicNeoForge(IEventBus eventBus) {

		// This method is invoked by the NeoForge mod loader when it is ready
		// to load your mod. You can access NeoForge and Common code in this
		// project.

		// Use NeoForge to bootstrap the Common mod.
		CommonClass.init();
		//? if < 1.20.6 {
		ModLoadingContext.get().registerExtensionPoint(
			ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory(
				(minecraft, parent) -> new ConstantMusicConfigScreen(parent)));
		//?}
	}
}