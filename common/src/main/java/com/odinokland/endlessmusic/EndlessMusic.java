package com.odinokland.endlessmusic;

import dev.architectury.injectables.annotations.ExpectPlatform;
import com.odinokland.endlessmusic.mixin.MusicManagerAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.network.chat.Component;

import java.util.List;

public class EndlessMusic {

    public static final String MODID = "endlessmusic";

    @ExpectPlatform
    public static boolean isClothConfigLoaded() {
        throw new RuntimeException();
    }

    public static int getTimer() {
        if(isClothConfigLoaded())
            return EndlessMusicConfig.getConfig().timer;
        return 0;
    }

    public static void addDebugText(List<String> texts) {
        MusicManager manager = Minecraft.getInstance().getMusicManager();
        if(manager instanceof MusicManagerAccessor accessor) {
            SoundInstance currentMusic = accessor.getCurrentMusic();
            if(currentMusic == null)
                texts.add(Component.translatable("endlessmusic.debug.timer", accessor.getTimer()).getString());
            else
                texts.add(Component.translatable("endlessmusic.debug.music", currentMusic.getLocation()).getString());
        }
    }
}
