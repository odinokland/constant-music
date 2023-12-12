package com.odinokland.endlessmusic;

import dev.architectury.injectables.annotations.ExpectPlatform;
import com.odinokland.endlessmusic.mixin.MusicManagerAccessor;
import dev.architectury.platform.Platform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.network.chat.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class EndlessMusic {

    public static final String MODID = "endlessmusic";
    public static final Path configFile = Paths.get(Platform.getConfigFolder().toString(), EndlessMusic.MODID + ".toml");
    private static boolean manualConfigInitialized = false;
    private static int timer = 0;

    @ExpectPlatform
    public static boolean isClothConfigLoaded() {
        throw new RuntimeException();
    }

    @ExpectPlatform
    public static int readValue() {
        throw new RuntimeException();
    }

    @ExpectPlatform
    public static void writeValue(int value) {
        throw new RuntimeException();
    }

    public static int getTimer() {
        if(isClothConfigLoaded())
            return EndlessMusicConfig.getConfig().timer;
        if (!manualConfigInitialized) {
            try {
                timer =  readValue();
                manualConfigInitialized = true;
            } catch (Exception e) {
                return 0;
            }
        }
        return timer;
    }

    public static void setTimer(int value) {
        if(isClothConfigLoaded()) {
            EndlessMusicConfig.setTimer(value);
        } else {
            writeValue(value);
            timer = value;
        }
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
