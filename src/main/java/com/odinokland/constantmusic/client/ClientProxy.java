package com.odinokland.constantmusic.client;

import com.odinokland.constantmusic.util.IModProxy;
//? if > 1.21.5 {
/*import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.sounds.MusicManager;
*///? }

/**
 * The type Client proxy.
 */
public class ClientProxy implements IModProxy {
	/**
	 * Default constructor.
	 */
	public ClientProxy() { }

	@Override
	public void updateMusicFrequencyOption(String frequencyName) {
		//? if > 1.21.5 {
		/*if (Minecraft.getInstance() != null) {
			Options options = Minecraft.getInstance().options;
			options.musicFrequency().set(MusicManager.MusicFrequency.valueOf(frequencyName));
			options.save();
		}
		*///? }
	}
}
