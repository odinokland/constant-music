package com.odinokland.constantmusic.util;

/**
 * The interface Mod proxy.
 */
public interface IModProxy {
	/**
	 * Update music frequency option.
	 *
	 * @param frequencyName the frequency name
	 */
// This will execute on the client to update options safely
	void updateMusicFrequencyOption(String frequencyName);
}
