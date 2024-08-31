package com.odinokland.constantmusic;

import com.odinokland.constantmusic.gui.ConstantMusicConfigScreen;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

public class ClientConfigHelper implements IConfigScreenFactory {
    @Override
    public Screen createScreen(ModContainer container, Screen modListScreen) {
        return new ConstantMusicConfigScreen(modListScreen);
    }
}
