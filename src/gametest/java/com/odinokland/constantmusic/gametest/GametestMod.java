package com.odinokland.constantmusic.gametest;

//? forge || neoforge {
//? forge {
import net.minecraftforge.fml.common.Mod;
//? }
//? neoforge {
//import net.neoforged.fml.common.Mod;
//? }

@Mod(GameTestConstants.MOD_ID)
public class GametestMod {
	public GametestMod() {
		GameTestConstants.LOGGER.info("Gametest Mod initialized");
	}
}
//? }
