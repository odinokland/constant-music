package com.odinokland.constantmusic.platform.fabric;

//? fabric {
/*
//? fabric {
/^
//? fabric {
/^
import com.odinokland.constantmusic.gui.ConstantMusicConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/^*
 * The type Constant music mod menu integration.
 ^/
@Environment(EnvType.CLIENT)
public class FabricModMenuIntegration implements ModMenuApi {
	/^*
	 * Gets mod config screen factory.
	 *
	 * @return the mod config screen factory
	 ^/
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return ConstantMusicConfigScreen::new;
	}
}
^///?}
^///?}
*///?}
