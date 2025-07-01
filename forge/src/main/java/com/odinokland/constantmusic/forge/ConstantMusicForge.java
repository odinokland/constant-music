package com.odinokland.constantmusic.forge;

import com.odinokland.constantmusic.common.CommonClass;
import com.odinokland.constantmusic.common.Constants;
import net.minecraftforge.fml.common.Mod;

/**
 * The type Constant music.
 */
@Mod(Constants.MOD_ID)
public class ConstantMusicForge {

	/**
	 * Instantiates a new Constant music.
	 */
	public ConstantMusicForge() {
		// Use Forge to bootstrap the Common mod.
		// Constants.LOG.info("HELLO FROM " + Services.PLATFORM.getPlatformName());
		CommonClass.init();
	}
}