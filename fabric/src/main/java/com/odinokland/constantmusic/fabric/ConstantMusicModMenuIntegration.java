package com.odinokland.constantmusic.fabric;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import com.odinokland.constantmusic.ConstantMusic;
import com.odinokland.constantmusic.ConstantMusicConfig;
import me.shedaniel.autoconfig.AutoConfig;

public class ConstantMusicModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if(ConstantMusic.isClothConfigLoaded())
            return screen -> AutoConfig.getConfigScreen(ConstantMusicConfig.class, screen).get();
        return screen -> null;
    }
}
