package com.odinokland.constantmusic.gui;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * The type Config options list.
 */
public class ConfigOptionsList  extends ContainerObjectSelectionList<ConfigOptionsList.Entry> {
    private static final int BIG_BUTTON_WIDTH = 310;
    private static final int DEFAULT_ITEM_HEIGHT = 25;
    private final ConstantMusicConfigScreen screen;

    /**
     * Instantiates a new Config options list.
     *
     * @param mc     the mc
     * @param width  the width
     * @param screen the screen
     */
    public ConfigOptionsList(Minecraft mc, int width, ConstantMusicConfigScreen screen) {
        super(mc, width, screen.layout.getContentHeight(), screen.layout.getHeaderHeight(), DEFAULT_ITEM_HEIGHT);
        this.centerListVertically = false;
        this.screen = screen;
    }

    /**
     * Add big.
     *
     * @param option the option
     */
    public void addBig(OptionInstance<?> option) {
        this.addEntry(ConfigOptionsList.OptionEntry.big(this.minecraft.options, option, this.screen));
    }

    /**
     * Add small.
     *
     * @param options the options
     */
    public void addSmall(OptionInstance<?>... options) {
        for (int i = 0; i < options.length; i += 2) {
            OptionInstance<?> optioninstance = i < options.length - 1 ? options[i + 1] : null;
            this.addEntry(ConfigOptionsList.OptionEntry.small(this.minecraft.options, options[i], optioninstance, this.screen));
        }
    }

    /**
     * Add small.
     *
     * @param options the options
     */
    public void addSmall(List<AbstractWidget> options) {
        for (int i = 0; i < options.size(); i += 2) {
            this.addSmall(options.get(i), i < options.size() - 1 ? options.get(i + 1) : null);
        }
    }

    /**
     * Add small.
     *
     * @param left  the left
     * @param right the right
     */
    public void addSmall(AbstractWidget left, AbstractWidget right) {
        this.addEntry(ConfigOptionsList.Entry.small(left, right, this.screen));
    }

    @Override
    public int getRowWidth() {
        return BIG_BUTTON_WIDTH;
    }

    /**
     * The type Entry.
     */
    protected static class Entry extends ContainerObjectSelectionList.Entry<ConfigOptionsList.Entry> {
        private final List<AbstractWidget> children;
        private final Screen screen;
        private static final int X_OFFSET = 160;

        /**
         * Instantiates a new Entry.
         *
         * @param pChildren the p children
         * @param pScreen   the p screen
         */
        Entry(List<AbstractWidget> pChildren, Screen pScreen) {
            this.children = ImmutableList.copyOf(pChildren);
            this.screen = pScreen;
        }

        /**
         * Big config options list . entry.
         *
         * @param pOptions the p options
         * @param pScreen  the p screen
         * @return the config options list . entry
         */
        public static ConfigOptionsList.Entry big(List<AbstractWidget> pOptions, Screen pScreen) {
            return new ConfigOptionsList.Entry(pOptions, pScreen);
        }

        /**
         * Small config options list . entry.
         *
         * @param pLeftOption  the p left option
         * @param pRightOption the p right option
         * @param pScreen      the p screen
         * @return the config options list . entry
         */
        public static ConfigOptionsList.Entry small(AbstractWidget pLeftOption, AbstractWidget pRightOption, Screen pScreen) {
            return pRightOption == null ? new ConfigOptionsList.Entry(ImmutableList.of(pLeftOption), pScreen) : new ConfigOptionsList.Entry(ImmutableList.of(pLeftOption, pRightOption), pScreen);
        }

        @Override
        public void render(
                @NotNull GuiGraphics pGuiGraphics,
                int pIndex,
                int pTop,
                int pLeft,
                int pWidth,
                int pHeight,
                int pMouseX,
                int pMouseY,
                boolean pHovering,
                float pPartialTick
        ) {
            int i = 0;
            int j = this.screen.width / 2 - 155;

            for(Iterator<AbstractWidget> var13 = this.children.iterator(); var13.hasNext(); i += 160) {
                AbstractWidget abstractwidget = (AbstractWidget)var13.next();
                abstractwidget.setPosition(j + i, pTop);
                abstractwidget.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
            }
        }

        @Override
        public @NotNull List<? extends GuiEventListener> children() {
            return this.children;
        }

        @Override
        public @NotNull List<? extends NarratableEntry> narratables() {
            return this.children;
        }
    }

    /**
     * The type Option entry.
     */
    protected static class OptionEntry extends ConfigOptionsList.Entry {
        /**
         * The Options.
         */
        final Map<OptionInstance<?>, AbstractWidget> options;

        private OptionEntry(Map<OptionInstance<?>, AbstractWidget> pOptions, ConstantMusicConfigScreen pScreen) {
            super(ImmutableList.copyOf(pOptions.values()), pScreen);
            this.options = pOptions;
        }

        /**
         * Big config options list . option entry.
         *
         * @param pOptions the p options
         * @param pOption  the p option
         * @param pScreen  the p screen
         * @return the config options list . option entry
         */
        public static ConfigOptionsList.OptionEntry big(Options pOptions, OptionInstance<?> pOption, ConstantMusicConfigScreen pScreen) {
            return new ConfigOptionsList.OptionEntry(ImmutableMap.of(pOption, pOption.createButton(pOptions, 0, 0, 310)), pScreen);
        }

        /**
         * Small config options list . option entry.
         *
         * @param pOptions     the p options
         * @param pLeftOption  the p left option
         * @param pRightOption the p right option
         * @param pScreen      the p screen
         * @return the config options list . option entry
         */
        public static ConfigOptionsList.OptionEntry small(Options pOptions, OptionInstance<?> pLeftOption, OptionInstance<?> pRightOption, ConstantMusicConfigScreen pScreen) {
            AbstractWidget abstractwidget = pLeftOption.createButton(pOptions);
            return pRightOption == null ? new ConfigOptionsList.OptionEntry(ImmutableMap.of(pLeftOption, abstractwidget), pScreen) : new ConfigOptionsList.OptionEntry(ImmutableMap.of(pLeftOption, abstractwidget, pRightOption, pRightOption.createButton(pOptions)), pScreen);
        }
    }
}
