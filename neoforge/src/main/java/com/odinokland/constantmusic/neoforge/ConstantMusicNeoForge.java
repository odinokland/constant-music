package com.odinokland.constantmusic.neoforge;

import com.odinokland.constantmusic.common.CommonClass;
import com.odinokland.constantmusic.common.Constants;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod(Constants.MOD_ID)
public class ConstantMusicNeoForge {

	public ConstantMusicNeoForge(IEventBus eventBus) {

		// This method is invoked by the NeoForge mod loader when it is ready
		// to load your mod. You can access NeoForge and Common code in this
		// project.

		// Use NeoForge to bootstrap the Common mod.
		CommonClass.init();
	}
}