package com.odinokland.constantmusic.gui;

import com.odinokland.constantmusic.CommonClass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LayoutSettings;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
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
    protected ConfigOptionsList list;
    /**
     * The Layout.
     */
    public final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);

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
        this.addTitle();
        this.addContents();
        this.addFooter();
        this.layout.visitWidgets((t) -> {
            AbstractWidget abstractwidget = this.addRenderableWidget(t);
        });
        this.repositionElements();
    }

    /**
     * Add title.
     */
    protected void addTitle() {
        LinearLayout linearlayout = LinearLayout.vertical().spacing(4);
        linearlayout.addChild(new StringWidget(this.title, this.font), LayoutSettings::alignHorizontallyCenter);
        linearlayout.addChild(new StringWidget(Component.translatable("constantmusic.option.timer"), this.font));
        this.layout.addToHeader(linearlayout);
    }

    /**
     * Add contents.
     */
    protected void addContents() {
        this.list = this.layout.addToContents(new ConfigOptionsList(this.minecraft, this.width, this));
        this.addOptions();
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

    /**
     * Add footer.
     */
    protected void addFooter() {
        final Minecraft client = Minecraft.getInstance();
        final Button.Builder doneButton = Button.builder(CommonComponents.GUI_DONE, button -> client.setScreen(this.parent));
        this.layout.addToFooter(doneButton.width(200).build());
    }

    @Override
    protected void repositionElements() {
        this.layout.arrangeElements();
        if (this.list != null) {
            this.list.updateSize(this.width, this.layout);
        }
    }
}
