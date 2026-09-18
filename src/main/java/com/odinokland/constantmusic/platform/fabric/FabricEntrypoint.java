package com.odinokland.constantmusic.platform.fabric;

//? fabric {

/*import com.odinokland.constantmusic.ConstantMusic;
import com.odinokland.constantmusic.util.JukeboxTrackerUtility;
import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
//? if >= 26.1 {
//import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLevelEvents;
//? } else if >=1.21.6 {
//import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientWorldEvents;
 //?} else {
import com.odinokland.constantmusic.platform.fabric.client.event.ClientWorldEvents;
//?}

/^*
 * The type Fabric entrypoint.
 ^/
@Entrypoint("main")
public class FabricEntrypoint implements ModInitializer, ClientModInitializer {

	/^*
	 * Default constructor for FabricEntrypoint.
	 ^/
	public FabricEntrypoint() {
	}

	/^*
	 * On initialize.
	 ^/
	@Override
	public void onInitialize() {

	}

	/^*
	 * On initialize client.
	 ^/
	@Override
	public void onInitializeClient() {
		ConstantMusic.init();
		//? if >= 26.1 {
		//ClientLevelEvents.AFTER_CLIENT_LEVEL_CHANGE.register((client, world) -> JukeboxTrackerUtility.clearJukeboxes());
		//? } else {
		ClientWorldEvents.AFTER_CLIENT_WORLD_CHANGE.register((client, world) -> JukeboxTrackerUtility.clearJukeboxes());
		//? }
		ClientTickEvents.END_CLIENT_TICK.register(JukeboxTrackerUtility::checkJukeboxesInRange);
	}
}
*///?}
