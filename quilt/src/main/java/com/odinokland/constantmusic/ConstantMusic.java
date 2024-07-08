package com.odinokland.constantmusic;

import com.odinokland.constantmusic.platform.Services;
import net.minecraft.network.chat.Component;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class ConstantMusic implements ModInitializer, ClientModInitializer {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }

    @Override
    public void onInitialize(ModContainer mod) {

    }

    @Override
    public void onInitializeClient(ModContainer mod) {
        //Constants.LOG.info("Hello Fabric world!" + Component.translatable(Constants.MOD_ID + ".option"));
        CommonClass.init();
    }
}