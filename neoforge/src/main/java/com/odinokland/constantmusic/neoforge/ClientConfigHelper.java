//? if >=1.20.6 {
/*package com.odinokland.constantmusic.neoforge;

import com.odinokland.constantmusic.common.gui.ConstantMusicConfigScreen;
import net.minecraft.client.gui.screens.Screen;
//? if >=1.21 {
/^import net.neoforged.fml.ModContainer;
^///?} else {
import net.minecraft.client.Minecraft;
//?}
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

public class ClientConfigHelper implements IConfigScreenFactory {
    @Override
    public Screen createScreen(/^? >=1.21 {^/ /^ModContainer ^//^?} else {^/ Minecraft /^?}^/ container, Screen modListScreen) {
        return new ConstantMusicConfigScreen(modListScreen);
    }
}
*///?}
