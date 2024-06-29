package com.odinokland.constantmusic;

import com.odinokland.constantmusic.gui.ConstantMusicConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.jetbrains.annotations.NotNull;

public class ClientConfigHelper implements IConfigScreenFactory {
    @Override
    public @NotNull Screen createScreen(@NotNull Minecraft minecraft, @NotNull Screen screen) {
        return new ConstantMusicConfigScreen(screen);
    }
}
