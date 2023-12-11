package com.odinokland.endlessmusic.fabric;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import com.odinokland.endlessmusic.EndlessMusic;
import com.odinokland.endlessmusic.EndlessMusicConfig;
import me.shedaniel.autoconfig.AutoConfig;

public class EndlessMusicModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if(EndlessMusic.isClothConfigLoaded())
            return screen -> AutoConfig.getConfigScreen(EndlessMusicConfig.class, screen).get();
        return screen -> null;
    }
}
