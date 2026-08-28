package com.odinokland.constantmusic.platform.forge;

//? forge {
/*
import com.odinokland.constantmusic.Constants;
import com.odinokland.constantmusic.util.JukeboxTrackerUtility;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.LevelEvent;

/^*
 * The type Client handler.
 ^/
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventSubscriber {

	/^*
	 * On client tick.
	 *
	 * @param event the event
	 ^/
	@SubscribeEvent
	public static void onClientTick(/^? >= 1.21.6 {^/ /^TickEvent.ClientTickEvent.Post ^//^?} else {^/ TickEvent.ClientTickEvent /^?}^/ event) {
		Minecraft client = Minecraft.getInstance();
		JukeboxTrackerUtility.checkJukeboxesInRange(client);
	}

	/^*
	 * On level unload.
	 *
	 * @param event the event
	 ^/
	@SubscribeEvent
	public static void onLevelUnload(LevelEvent.Unload event) {
		JukeboxTrackerUtility.clearJukeboxes();
	}
}
*///?}
