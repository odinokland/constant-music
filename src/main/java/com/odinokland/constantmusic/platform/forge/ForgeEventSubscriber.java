package com.odinokland.constantmusic.platform.forge;

//? forge {

import com.odinokland.constantmusic.util.Constants;
import com.odinokland.constantmusic.core.JukeboxTrackerUtility;
import com.odinokland.constantmusic.util.DevTools;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.TickEvent;
//? >= 1.19.0 {
import net.minecraftforge.event.level.LevelEvent.Unload;
//? } else {
//import net.minecraftforge.event.world.WorldEvent.Unload;
//? }

/**
 * The type Client handler.
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventSubscriber {

	/**
	 * Constructor for the ForgeEventSubscriber.
	 */
	public ForgeEventSubscriber() {
	}

	/**
	 * On client tick.
	 *
	 * @param event the event
	 */
	@SubscribeEvent
	public static void onClientTick(/*? >=1.21.6 {*/ /*TickEvent.ClientTickEvent.Post *//*?} else {*/ TickEvent.ClientTickEvent /*?}*/ event) {
		Minecraft client = Minecraft.getInstance();
		JukeboxTrackerUtility.checkJukeboxesInRange(client);
	}

	/**
	 * On level unload.
	 *
	 * @param event the event
	 */
	@SubscribeEvent
	public static void onLevelUnload(Unload event) {
		JukeboxTrackerUtility.clearJukeboxes();
	}
}
//?}
