package com.odinokland.constantmusic.neoforge;

import com.odinokland.constantmusic.common.Constants;
import com.odinokland.constantmusic.common.util.JukeboxTrackerUtility;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
//? if >= 1.20.6 {
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
//?} else {
/*import net.neoforged.neoforge.event.TickEvent;
*///?}
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.level.LevelEvent;


//? if >= 1.21.6 {
@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
//?} else if >= 1.20.6 {
/*@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
*///?} else {
/*@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
*///?}
public class ClientJukeboxHandler {
	@SubscribeEvent
	public static void onClientTick(/*? >= 1.20.6 {*/ ClientTickEvent.Post /*?} else {*/ /*TickEvent.ClientTickEvent *//*?}*/ event) {
		Minecraft client = Minecraft.getInstance();
		JukeboxTrackerUtility.checkJukeboxesInRange(client);
	}

	@SubscribeEvent
	public static void onLevelUnload(LevelEvent.Unload event) {
		JukeboxTrackerUtility.clearJukeboxes();
	}
}