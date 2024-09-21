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

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The type Options list.
 */
public class OptionsList extends ContainerObjectSelectionList<OptionsList.Entry> {
    private static final int BIG_BUTTON_WIDTH = 310;
    private static final int SMALL_BUTTON_WIDTH = 150;
    private static final int DEFAULT_ITEM_HEIGHT = 25;
    private final Screen screen;

    /**
     * Instantiates a new Options list.
     *
     * @param minecraft    the minecraft
     * @param width        the width
     * @param height       the height
     * @param headerHeight the header height
     * @param footerHeight the footer height
     * @param screen       the screen
     */
    public OptionsList(Minecraft minecraft, int width, int height, int headerHeight,int footerHeight, Screen screen) {
        super(minecraft, width, height - headerHeight - footerHeight, headerHeight, DEFAULT_ITEM_HEIGHT);
        this.centerListVertically = false;
        this.screen = screen;
    }

    /**
     * Add big.
     *
     * @param optionInstance the option instance
     */
    public void addBig(OptionInstance<?> optionInstance) {
        this.addEntry(OptionEntry.big(this.minecraft.options, optionInstance, this.screen));
    }

    /**
     * Add small.
     *
     * @param optionInstances the option instances
     */
    public void addSmall(OptionInstance<?>... optionInstances) {
        for (int i = 0; i < optionInstances.length; i += 2) {
            OptionInstance<?> optionInstance = i < optionInstances.length - 1 ? optionInstances[i + 1] : null;
            this.addEntry(OptionEntry.small(this.minecraft.options, optionInstances[i], optionInstance, this.screen));
        }
    }

    /**
     * Add small.
     *
     * @param list the list
     */
    public void addSmall(List<AbstractWidget> list) {
        for (int i = 0; i < list.size(); i += 2) {
            this.addSmall((AbstractWidget)list.get(i), i < list.size() - 1 ? (AbstractWidget)list.get(i + 1) : null);
        }
    }

    /**
     * Add small.
     *
     * @param abstractWidget  the abstract widget
     * @param abstractWidget2 the abstract widget 2
     */
    public void addSmall(AbstractWidget abstractWidget, AbstractWidget abstractWidget2) {
        this.addEntry(Entry.small(abstractWidget, abstractWidget2, this.screen));
    }

    @Override
    public int getRowWidth() {
        return BIG_BUTTON_WIDTH;
    }

    /**
     * Find option abstract widget.
     *
     * @param optionInstance the option instance
     * @return the abstract widget
     */
    public AbstractWidget findOption(OptionInstance<?> optionInstance) {
        for (Entry entry : this.children()) {
            if (entry instanceof OptionEntry optionEntry) {
                AbstractWidget abstractWidget = (AbstractWidget)optionEntry.options.get(optionInstance);
                if (abstractWidget != null) {
                    return abstractWidget;
                }
            }
        }

        return null;
    }

    /**
     * Gets mouse over.
     *
     * @param d the d
     * @param e the e
     * @return the mouse over
     */
    public Optional<GuiEventListener> getMouseOver(double d, double e) {
        for (Entry entry : this.children()) {
            for (GuiEventListener guiEventListener : entry.children()) {
                if (guiEventListener.isMouseOver(d, e)) {
                    return Optional.of(guiEventListener);
                }
            }
        }

        return Optional.empty();
    }

    /**
     * The type Entry.
     */
    protected static class Entry extends ContainerObjectSelectionList.Entry<Entry> {
        private final List<AbstractWidget> children;
        private final Screen screen;
        private static final int X_OFFSET = 160;

        /**
         * Instantiates a new Entry.
         *
         * @param list   the list
         * @param screen the screen
         */
        Entry(List<AbstractWidget> list, Screen screen) {
            this.children = ImmutableList.copyOf(list);
            this.screen = screen;
        }

        /**
         * Big entry.
         *
         * @param list   the list
         * @param screen the screen
         * @return the entry
         */
        public static Entry big(List<AbstractWidget> list, Screen screen) {
            return new Entry(list, screen);
        }

        /**
         * Small entry.
         *
         * @param abstractWidget  the abstract widget
         * @param abstractWidget2 the abstract widget 2
         * @param screen          the screen
         * @return the entry
         */
        public static Entry small(AbstractWidget abstractWidget, AbstractWidget abstractWidget2, Screen screen) {
            return abstractWidget2 == null
                    ? new Entry(ImmutableList.of(abstractWidget), screen)
                    : new Entry(ImmutableList.of(abstractWidget, abstractWidget2), screen);
        }

        @Override
        public void render(GuiGraphics guiGraphics, int i, int j, int k, int l, int m, int n, int o, boolean bl, float f) {
            int p = 0;
            int q = this.screen.width / 2 - 155;

            for (AbstractWidget abstractWidget : this.children) {
                abstractWidget.setPosition(q + p, j);
                abstractWidget.render(guiGraphics, n, o, f);
                p += X_OFFSET;
            }
        }

        @Override
        public List<? extends GuiEventListener> children() {
            return this.children;
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return this.children;
        }
    }

    /**
     * The type Option entry.
     */
    protected static class OptionEntry extends Entry {
        /**
         * The Options.
         */
        final Map<OptionInstance<?>, AbstractWidget> options;

        private OptionEntry(Map<OptionInstance<?>, AbstractWidget> map, Screen screen) {
            super(ImmutableList.copyOf(map.values()), screen);
            this.options = map;
        }

        /**
         * Big option entry.
         *
         * @param options        the options
         * @param optionInstance the option instance
         * @param screen         the screen
         * @return the option entry
         */
        public static OptionEntry big(Options options, OptionInstance<?> optionInstance, Screen screen) {
            return new OptionEntry(ImmutableMap.of(optionInstance, optionInstance.createButton(options, 0, 0, BIG_BUTTON_WIDTH)), screen);
        }

        /**
         * Small option entry.
         *
         * @param options         the options
         * @param optionInstance  the option instance
         * @param optionInstance2 the option instance 2
         * @param screen          the screen
         * @return the option entry
         */
        public static OptionEntry small(
                Options options, OptionInstance<?> optionInstance, OptionInstance<?> optionInstance2, Screen screen
        ) {
            AbstractWidget abstractWidget = optionInstance.createButton(options, 0, 0, SMALL_BUTTON_WIDTH);
            return optionInstance2 == null
                    ? new OptionEntry(ImmutableMap.of(optionInstance, abstractWidget), screen)
                    : new OptionEntry(ImmutableMap.of(optionInstance, abstractWidget, optionInstance2, optionInstance2.createButton(options, 0, 0, SMALL_BUTTON_WIDTH)), screen);
        }
    }
}
