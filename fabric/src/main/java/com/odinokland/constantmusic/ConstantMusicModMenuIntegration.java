package com.odinokland.constantmusic;

import com.odinokland.constantmusic.platform.Services;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ConstantMusicModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if(Services.PLATFORM.isClothConfigLoaded())
            return screen -> AutoConfig.getConfigScreen(ConstantMusicConfig.class, screen).get();
        return screen -> null;
    }
}
