package com.odinokland.constantmusic;

import net.minecraftforge.fml.common.Mod;

/**
 * The type Constant music.
 */
@Mod(Constants.MOD_ID)
public class ConstantMusic {

    /**
     * Instantiates a new Constant music.
     */
    public ConstantMusic() {
        // Use Forge to bootstrap the Common mod.
        // Constants.LOG.info("HELLO FROM " + Services.PLATFORM.getPlatformName());
        CommonClass.init();
    }
}