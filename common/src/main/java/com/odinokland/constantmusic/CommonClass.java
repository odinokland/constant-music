package com.odinokland.constantmusic;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import com.odinokland.constantmusic.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
// however it will be compatible with all supported mod loaders.
public class CommonClass {

    public static final Path configFile = Paths.get(Services.PLATFORM.getConfigFolder().toString(), Constants.MOD_ID + ".toml");
    private static boolean manualConfigInitialized = false;
    private static int timer = 0;

    // The loader specific projects are able to import and use any code from the common project. This allows you to
    // write the majority of your code here and load it from your loader specific projects. This example has some
    // code that gets invoked by the entry point of the loader specific projects.
    public static void init() {

        Constants.LOG.info("Hello from Common init on {}! we are currently in a {} environment!", Services.PLATFORM.getPlatformName(), Services.PLATFORM.getEnvironmentName());
        Constants.LOG.info("The ID for diamonds is {}", BuiltInRegistries.ITEM.getKey(Items.DIAMOND));

        // It is common for all supported loaders to provide a similar feature that can not be used directly in the
        // common code. A popular way to get around this is using Java's built-in service loader feature to create
        // your own abstraction layer. You can learn more about this in our provided services class. In this example
        // we have an interface in the common code and use a loader specific implementation to delegate our call to
        // the platform specific approach.
        if (Services.PLATFORM.isModLoaded(Constants.MOD_ID)) {

            Constants.LOG.info("Hello to examplemod");
        }
    }

    public static int readValue() {
        Toml toml = new Toml().read(new File(CommonClass.configFile.toString()));
        return toml.getLong("timer").intValue();
    }

    public static void writeValue(int value) {
        TomlWriter tomlWriter = new TomlWriter();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("timer", value);
            tomlWriter.write(map, new File(CommonClass.configFile.toString()));
        } catch (IOException e) {
            // throw new RuntimeException(e);
        }
    }

    public static int getTimer() {
        if(Services.PLATFORM.isClothConfigLoaded())
            return ConstantMusicConfig.getConfig().timer;
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
        if(Services.PLATFORM.isClothConfigLoaded()) {
            ConstantMusicConfig.setTimer(value);
        } else {
            writeValue(value);
            timer = value;
        }
    }
}