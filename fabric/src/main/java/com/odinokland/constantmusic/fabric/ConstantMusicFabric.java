package com.odinokland.constantmusic.fabric;

import com.odinokland.constantmusic.common.CommonClass;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

public class ConstantMusicFabric implements ModInitializer, ClientModInitializer {
	@Override
	public void onInitializeClient() {

	}
	@Override
	public void onInitialize() {

		// This method is invoked by the Fabric mod loader when it is ready
		// to load your mod. You can access Fabric and Common code in this
		// project.

		// Use Fabric to bootstrap the Common mod.
		//Constants.LOG.info("Hello Fabric world!");
		CommonClass.init();
	}
}
