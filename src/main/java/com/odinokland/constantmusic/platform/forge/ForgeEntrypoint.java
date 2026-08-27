package com.odinokland.constantmusic.platform.forge;

//? forge {

import com.odinokland.constantmusic.ConstantMusic;
import com.odinokland.constantmusic.Constants;
import net.minecraftforge.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class ForgeEntrypoint {

	public ForgeEntrypoint() {
		ConstantMusic.init();
	}
}
//?}
