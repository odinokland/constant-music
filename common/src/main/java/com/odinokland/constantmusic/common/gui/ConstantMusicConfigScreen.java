package com.odinokland.constantmusic.common.gui;

import com.odinokland.constantmusic.common.CommonClass;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.Button;
//? if >=1.21 {
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LayoutSettings;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
//?} else if >1.19.4 {
/*import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.NotNull;
*///?} else {
/*import com.mojang.blaze3d.vertex.PoseStack;
*///?}
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

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
	//? if >=1.21 {
    /**
     * The Layout.
     */
    public final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);
	//?}

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
		//? if >=1.21 {
        this.addTitle();
        this.addContents();
        this.addFooter();
        this.layout.visitWidgets((t) -> {
            AbstractWidget abstractwidget = this.addRenderableWidget(t);
        });
        this.repositionElements();
		//?} else {
		/*this.list = this.addRenderableWidget(new OptionsList(this.minecraft, this.width, this.height, 32, 32, this));
		this.addOptions();
		//? if >= 1.19.3 {
		this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, (Button button) -> {
			this.minecraft.setScreen(this.parent);
		}).bounds(this.width / 2 - 100, this.height - 27, 200, 20).build());
		//?} else {
		/^this.addRenderableWidget(new Button(this.width / 2 - 100, this.height - 27, 200, 20, CommonComponents.GUI_DONE, (Button button) -> {
			this.minecraft.setScreen(this.parent);
		}));
		^///?}
		*///?}
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

	//? if >=1.21 {
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
        this.list = this.layout.addToContents(new OptionsList(this.minecraft, this.width, this));
        this.addOptions();
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
	//?} else if >1.19.4 {
	/*public void render(@NotNull GuiGraphics gui, int i, int j, float f) {
		super.render(gui, i, j, f);
		gui.drawCenteredString(this.font, this.title, this.width / 2, 20, 16777215);
	}
	*///?} else {
		/*public void render(@NotNull PoseStack gui, int i, int j, float f) {
			super.render(gui, i, j, f);
			drawCenteredString(gui, this.font, this.title, this.width / 2, 20, 16777215);
		}
	*///?}
}
