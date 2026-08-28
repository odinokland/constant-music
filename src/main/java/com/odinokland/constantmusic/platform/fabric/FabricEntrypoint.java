package com.odinokland.constantmusic.platform.fabric;

//? fabric {

import com.odinokland.constantmusic.ConstantMusic;
import com.odinokland.constantmusic.util.JukeboxTrackerUtility;
import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
//? if >=1.21.6 {
//import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientWorldEvents;
 //?} else {
import com.odinokland.constantmusic.platform.fabric.client.event.ClientWorldEvents;
//?}

@Entrypoint("main")
public class FabricEntrypoint implements ModInitializer, ClientModInitializer {

	/**
	 * On initialize.
	 */
	@Override
	public void onInitialize() {

	}

	/**
	 * On initialize client.
	 */
	@Override
	public void onInitializeClient() {
		ConstantMusic.init();
		ClientWorldEvents.AFTER_CLIENT_WORLD_CHANGE.register((client, world) -> JukeboxTrackerUtility.clearJukeboxes());
		ClientTickEvents.END_CLIENT_TICK.register(JukeboxTrackerUtility::checkJukeboxesInRange);
	}
}
//?}
