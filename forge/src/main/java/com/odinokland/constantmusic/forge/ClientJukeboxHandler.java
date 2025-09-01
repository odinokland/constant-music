package com.odinokland.constantmusic.forge;

import com.odinokland.constantmusic.common.Constants;
import com.odinokland.constantmusic.common.util.JukeboxTrackerUtility;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
//? if >=1.21.6 {
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
 //?} else {
/*import net.minecraftforge.eventbus.api.SubscribeEvent;
*///?}
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.LevelEvent;

/**
 * The type Client handler.
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientJukeboxHandler {
	/**
	 * On client tick.
	 *
	 * @param event the event
	 */
	@SubscribeEvent
	public static void onClientTick(/*? >= 1.21.6 {*/ TickEvent.ClientTickEvent.Post /*?} else {*/ /*TickEvent.ClientTickEvent *//*?}*/ event) {
		Minecraft client = Minecraft.getInstance();
		JukeboxTrackerUtility.checkJukeboxesInRange(client);
	}

	/**
	 * On level unload.
	 *
	 * @param event the event
	 */
	@SubscribeEvent
	public static void onLevelUnload(LevelEvent.Unload event) {
		JukeboxTrackerUtility.clearJukeboxes();
	}
}