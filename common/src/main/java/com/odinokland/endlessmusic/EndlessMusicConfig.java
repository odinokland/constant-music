package com.odinokland.endlessmusic;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = EndlessMusic.MODID)
public class EndlessMusicConfig implements ConfigData {

    @ConfigEntry.BoundedDiscrete(min = 0, max = 200)
    public int timer = 0;

    public static EndlessMusicConfig getConfig() {
        return AutoConfig.getConfigHolder(EndlessMusicConfig.class).getConfig();
    }

    public static void setTimer(int value) {
        ConfigHolder<EndlessMusicConfig> holder = AutoConfig.getConfigHolder(EndlessMusicConfig.class);
        EndlessMusicConfig config = holder.getConfig();
        config.timer = value;
        holder.setConfig(config);
        holder.save();
    }
}
