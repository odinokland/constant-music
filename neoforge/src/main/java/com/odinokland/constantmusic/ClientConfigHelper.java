package com.odinokland.constantmusic;

import com.odinokland.constantmusic.gui.ConstantMusicConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

public class ClientConfigHelper implements IConfigScreenFactory {
    @Override
    public Screen createScreen(Minecraft minecraft, Screen modListScreen) {
        return new ConstantMusicConfigScreen(modListScreen);
    }
}
