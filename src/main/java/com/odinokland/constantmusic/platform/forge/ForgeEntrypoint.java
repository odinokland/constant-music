package com.odinokland.constantmusic.platform.forge;

//? forge {

import com.odinokland.constantmusic.ConstantMusic;
import com.odinokland.constantmusic.Constants;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

//? if <1.21.6 {
import net.minecraftforge.eventbus.api.IEventBus;
//? } else {
//import net.minecraftforge.eventbus.api.bus.BusGroup;
//?}

//? if >=1.19 {
import net.minecraftforge.gametest.ForgeGameTestHooks;
import net.minecraftforge.registries.RegisterEvent;
//?} elif 1.18.2 {
/*import net.minecraftforge.client.ConfigGuiHandler;
import net.minecraftforge.client.ClientRegistry;
*///?} elif 1.17.1 {
/*import net.minecraftforge.fmlclient.ConfigGuiHandler;
import net.minecraftforge.fmlclient.registry.ClientRegistry;
*///?} elif 1.16.5 {
/*import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.client.registry.ClientRegistry;
*///?}

/**
 * The type Forge entrypoint.
 */
@Mod(Constants.MOD_ID)
public class ForgeEntrypoint {
	private static ModLoadingContext modLoadingContext;

	//? if 1.19.4 || >=1.20.6 {

	/*/^*
	 * Default constructor for Forge entrypoint.
	 * @param context The FMLJavaModLoadingContext for the mod.
	 ^/
	public ForgeEntrypoint(FMLJavaModLoadingContext context) {
		modLoadingContext = context;
	*///?} else {
	/**
	 * Instantiates a new Forge entrypoint.
	 */
	public ForgeEntrypoint() {
		modLoadingContext = ModLoadingContext.get();
		FMLJavaModLoadingContext context = FMLJavaModLoadingContext.get();
		//?}
		ConstantMusic.init();

		//? if >=1.21.6 {
		/*BusGroup modBusGroup = context.getModBusGroup();
		FMLClientSetupEvent.getBus(modBusGroup).addListener(ForgeEntrypoint::onClientSetup);
		*///?} else {
		IEventBus modEventBus = context.getModEventBus();
		modEventBus.addListener(ForgeEntrypoint::onClientSetup);
		//?}
	}

	/**
	 * On FML client setup event.
	 * @param event The FMLClientSetupEvent for the mod.
	 */
	public static void onClientSetup(final FMLClientSetupEvent event) {
		ForgeClientEntrypoint.setupConfigScreen(modLoadingContext);
	}
}
//?}
