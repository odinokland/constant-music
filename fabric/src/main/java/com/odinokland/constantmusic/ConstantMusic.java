package com.odinokland.constantmusic;

import com.odinokland.constantmusic.platform.Services;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

public class ConstantMusic implements ModInitializer, ClientModInitializer {
    @Override
    public void onInitializeClient() {
//        if(Services.PLATFORM.isClothConfigLoaded()) {
//            AutoConfig.register(ConstantMusicConfig.class, Toml4jConfigSerializer::new);
//        }
    }
    @Override
    public void onInitialize() {
        
        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        Constants.LOG.info("Hello Fabric world!");
        if(Services.PLATFORM.isClothConfigLoaded()) {
            AutoConfig.register(ConstantMusicConfig.class, Toml4jConfigSerializer::new);
        }
        CommonClass.init();
    }
}
