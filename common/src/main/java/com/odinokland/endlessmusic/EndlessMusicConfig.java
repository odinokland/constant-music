package com.odinokland.endlessmusic;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = EndlessMusic.MODID)
public class EndlessMusicConfig implements ConfigData {

    public int timer = 0;

    public static EndlessMusicConfig getConfig() {
        return AutoConfig.getConfigHolder(EndlessMusicConfig.class).getConfig();
    }
}
