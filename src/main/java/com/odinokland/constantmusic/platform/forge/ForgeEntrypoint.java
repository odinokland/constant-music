package com.odinokland.constantmusic.platform.forge;

//? forge {

import com.odinokland.constantmusic.ConstantMusic;
import com.odinokland.constantmusic.Constants;
import com.odinokland.constantmusic.gui.ConstantMusicConfigScreen;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * The type Forge entrypoint.
 */
@Mod(Constants.MOD_ID)
public class ForgeEntrypoint {

	/**
	 * Instantiates a new Forge entrypoint.
	 */
	public ForgeEntrypoint(FMLJavaModLoadingContext context) {
		ConstantMusic.init();
		MinecraftForge.registerConfigScreen(ConstantMusicConfigScreen::new);
	}
}
//?}
