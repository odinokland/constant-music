package com.odinokland.constantmusic.forge;

import com.odinokland.constantmusic.common.Constants;
import com.odinokland.constantmusic.common.gui.ConstantMusicConfigScreen;
import com.odinokland.constantmusic.common.util.JukeboxTrackerUtility;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
//? if >=1.21.6 {
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
//?} else {
/*import net.minecraftforge.eventbus.api.SubscribeEvent;
*///?}
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.LevelEvent;

/**
 * The type Client handler.
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientHandler {

    /**
     * On fml client setup event.
     *
     * @param event the event
     */
    @SubscribeEvent
    public static void onFMLClientSetupEvent(FMLClientSetupEvent event) {
		// TODO refactor this deprecation
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () ->
                new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> new ConstantMusicConfigScreen(parent))
        );
    }

	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent.Post event) {
		Minecraft client = Minecraft.getInstance();
		JukeboxTrackerUtility.checkJukeboxesInRange(client);
	}

	@SubscribeEvent
	public static void onLevelUnload(LevelEvent.Unload event) {
		JukeboxTrackerUtility.clearJukeboxes();
	}
}
