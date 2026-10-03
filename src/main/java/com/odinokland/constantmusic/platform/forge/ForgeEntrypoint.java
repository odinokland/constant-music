package com.odinokland.constantmusic.platform.forge;

//? forge {

import com.odinokland.constantmusic.ConstantMusic;
import com.odinokland.constantmusic.Constants;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * The type Forge entrypoint.
 */
@Mod(Constants.MOD_ID)
public class ForgeEntrypoint {
	/**
	 * Entrypoint Instance
	 */
	public static ForgeEntrypoint INSTANCE = null;
	/**
	 * FML Java Mod Loading Context
	 */
	public FMLJavaModLoadingContext context;

	//? if < 1.21.1 {
	/**
	 * Instantiates a new Forge entrypoint.
	 */
	public ForgeEntrypoint() {
		this(FMLJavaModLoadingContext.get());
	}
	//? }

	/**
	 * Default constructor for Forge entrypoint.
	 * @param context The FMLJavaModLoadingContext for the mod.
	 */
	public ForgeEntrypoint(FMLJavaModLoadingContext context) {
		Constants.LOG.info("Entrypoint hit with context");
		INSTANCE = this;
		this.context = context;
		ConstantMusic.init();
	}
}
//?}
