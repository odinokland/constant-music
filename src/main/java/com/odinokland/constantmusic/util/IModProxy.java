package com.odinokland.constantmusic.util;

public interface IModProxy {
	// This will execute on the client to update options safely
	void updateMusicFrequencyOption(String frequencyName);
}
