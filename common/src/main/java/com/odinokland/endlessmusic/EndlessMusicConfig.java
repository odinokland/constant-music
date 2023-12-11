package com.odinokland.endlessmusic;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = EndlessMusic.MODID)
public class EndlessMusicConfig implements ConfigData {

    @ConfigEntry.BoundedDiscrete(min = 0, max = 200)
    public int timer = 0;

    public static EndlessMusicConfig getConfig() {
        return AutoConfig.getConfigHolder(EndlessMusicConfig.class).getConfig();
    }
}
