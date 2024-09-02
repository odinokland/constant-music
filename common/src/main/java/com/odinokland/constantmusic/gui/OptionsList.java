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

public class OptionsList extends ContainerObjectSelectionList<OptionsList.Entry> {
    private static final int BIG_BUTTON_WIDTH = 310;
    private static final int SMALL_BUTTON_WIDTH = 150;
    private static final int DEFAULT_ITEM_HEIGHT = 25;
    private final Screen screen;

    public OptionsList(Minecraft minecraft, int width, int height, int headerHeight,int footerHeight, Screen screen) {
        super(minecraft, width, height - headerHeight - footerHeight, headerHeight, DEFAULT_ITEM_HEIGHT);
        this.centerListVertically = false;
        this.screen = screen;
    }

    public void addBig(OptionInstance<?> optionInstance) {
        this.addEntry(OptionsList.OptionEntry.big(this.minecraft.options, optionInstance, this.screen));
    }

    public void addSmall(OptionInstance<?>... optionInstances) {
        for (int i = 0; i < optionInstances.length; i += 2) {
            OptionInstance<?> optionInstance = i < optionInstances.length - 1 ? optionInstances[i + 1] : null;
            this.addEntry(OptionsList.OptionEntry.small(this.minecraft.options, optionInstances[i], optionInstance, this.screen));
        }
    }

    public void addSmall(List<AbstractWidget> list) {
        for (int i = 0; i < list.size(); i += 2) {
            this.addSmall((AbstractWidget)list.get(i), i < list.size() - 1 ? (AbstractWidget)list.get(i + 1) : null);
        }
    }

    public void addSmall(AbstractWidget abstractWidget, AbstractWidget abstractWidget2) {
        this.addEntry(OptionsList.Entry.small(abstractWidget, abstractWidget2, this.screen));
    }

    @Override
    public int getRowWidth() {
        return BIG_BUTTON_WIDTH;
    }

    public AbstractWidget findOption(OptionInstance<?> optionInstance) {
        for (OptionsList.Entry entry : this.children()) {
            if (entry instanceof OptionsList.OptionEntry optionEntry) {
                AbstractWidget abstractWidget = (AbstractWidget)optionEntry.options.get(optionInstance);
                if (abstractWidget != null) {
                    return abstractWidget;
                }
            }
        }

        return null;
    }

    public Optional<GuiEventListener> getMouseOver(double d, double e) {
        for (OptionsList.Entry entry : this.children()) {
            for (GuiEventListener guiEventListener : entry.children()) {
                if (guiEventListener.isMouseOver(d, e)) {
                    return Optional.of(guiEventListener);
                }
            }
        }

        return Optional.empty();
    }

    protected static class Entry extends ContainerObjectSelectionList.Entry<OptionsList.Entry> {
        private final List<AbstractWidget> children;
        private final Screen screen;
        private static final int X_OFFSET = 160;

        Entry(List<AbstractWidget> list, Screen screen) {
            this.children = ImmutableList.copyOf(list);
            this.screen = screen;
        }

        public static OptionsList.Entry big(List<AbstractWidget> list, Screen screen) {
            return new OptionsList.Entry(list, screen);
        }

        public static OptionsList.Entry small(AbstractWidget abstractWidget, AbstractWidget abstractWidget2, Screen screen) {
            return abstractWidget2 == null
                    ? new OptionsList.Entry(ImmutableList.of(abstractWidget), screen)
                    : new OptionsList.Entry(ImmutableList.of(abstractWidget, abstractWidget2), screen);
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

    protected static class OptionEntry extends OptionsList.Entry {
        final Map<OptionInstance<?>, AbstractWidget> options;

        private OptionEntry(Map<OptionInstance<?>, AbstractWidget> map, Screen screen) {
            super(ImmutableList.copyOf(map.values()), screen);
            this.options = map;
        }

        public static OptionsList.OptionEntry big(Options options, OptionInstance<?> optionInstance, Screen screen) {
            return new OptionsList.OptionEntry(ImmutableMap.of(optionInstance, optionInstance.createButton(options, 0, 0, BIG_BUTTON_WIDTH)), screen);
        }

        public static OptionsList.OptionEntry small(
                Options options, OptionInstance<?> optionInstance, OptionInstance<?> optionInstance2, Screen screen
        ) {
            AbstractWidget abstractWidget = optionInstance.createButton(options, 0, 0, SMALL_BUTTON_WIDTH);
            return optionInstance2 == null
                    ? new OptionsList.OptionEntry(ImmutableMap.of(optionInstance, abstractWidget), screen)
                    : new OptionsList.OptionEntry(ImmutableMap.of(optionInstance, abstractWidget, optionInstance2, optionInstance2.createButton(options, 0, 0, SMALL_BUTTON_WIDTH)), screen);
        }
    }
}
