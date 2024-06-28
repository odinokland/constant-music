package com.odinokland.constantmusic;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = Constants.MOD_ID)
public class ConstantMusicConfig implements ConfigData {

    @ConfigEntry.BoundedDiscrete(min = 0, max = 600)
    public int timer = 0;

    public static ConstantMusicConfig getConfig() {
        return AutoConfig.getConfigHolder(ConstantMusicConfig.class).getConfig();
    }

    public static void setTimer(int value) {
        ConfigHolder<ConstantMusicConfig> holder = AutoConfig.getConfigHolder(ConstantMusicConfig.class);
        ConstantMusicConfig config = holder.getConfig();
        config.timer = value;
        holder.setConfig(config);
        holder.save();
    }
}
