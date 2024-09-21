package com.odinokland.constantmusic.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.odinokland.constantmusic.CommonClass;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The type Constant music config screen.
 */
public class ConstantMusicConfigScreen extends Screen {
    private final Screen parent;
    /**
     * The List.
     */
    @Nullable
    protected OptionsList list;

    /**
     * Instantiates a new Constant music config screen.
     *
     * @param parent the parent
     */
    public ConstantMusicConfigScreen(final Screen parent) {
        super(Component.translatable("constantmusic.title"));
        this.parent = parent;
    }

    @Override
    public void init() {
        this.list = this.addRenderableWidget(new OptionsList(this.minecraft, this.width, this.height, 32, 32, this));
        this.addOptions();
        this.addRenderableWidget(new Button(this.width / 2 - 100, this.height - 27, 200, 20, CommonComponents.GUI_DONE, (Button button) -> {
            this.minecraft.setScreen(this.parent);
        }));
    }
    /**
     * Add options.
     */
    protected void addOptions() {
        OptionInstance<?>[] options = new OptionInstance[]{CommonClass.getConfigOption()};
        if (this.list != null) {
            this.list.addSmall(options);
        }
    }

    public void render(@NotNull PoseStack gui, int i, int j, float f) {
        super.render(gui, i, j, f);
        drawCenteredString(gui, this.font, this.title, this.width / 2, 20, 16777215);
    }
}
