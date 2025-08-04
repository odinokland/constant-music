package com.odinokland.constantmusic.forge;

import com.odinokland.constantmusic.common.Constants;
import com.odinokland.constantmusic.common.gui.ConstantMusicConfigScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
//? if > 1.21.6 {
/*import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
*///?} else {
import net.minecraftforge.eventbus.api.SubscribeEvent;
//?}
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

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


}
