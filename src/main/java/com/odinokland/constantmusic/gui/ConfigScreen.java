package com.odinokland.constantmusic.gui;

import com.odinokland.constantmusic.core.ConstantMusicManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.odinokland.constantmusic.util.TranslatableComponentHelper;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import org.jetbrains.annotations.NotNull;
//? if >= 1.19.0 {
import com.odinokland.constantmusic.util.Constants;
import com.odinokland.constantmusic.core.MusicFrequency;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.network.chat.Component;
import net.minecraft.client.OptionInstance;
//? }

import java.util.Arrays;
import java.util.function.Supplier;

/**
 * Constant music config screen.
 */
public class ConfigScreen extends Screen {
	private final Screen parent;

	/**
	 * Instantiates a new Constant music config screen.
	 *
	 * @param parent the parent
	 */
	public ConfigScreen(final Screen parent) {
		super(TranslatableComponentHelper.translatable("constantmusic.title"));
		this.parent = parent;
	}

	/**
	 * Called when the screen is initialized.
	 */
	@Override
	protected void init() {
		addRenderableWidget(ButtonGenerator.musicFrequencyButton(
				width / 2 - 100,
				height / 2 - 24,
				200
		));
		addRenderableWidget(ButtonGenerator.musicDelaySlider(
				width / 2 - 100,
				height / 2,
				200
		));
		//? if >= 1.19.3 {
		/*addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> onClose()).bounds(width / 2 - 100, height - 27, 200, 20).build());
		*///?} else {
		addRenderableWidget(new Button(width / 2 - 100, height / 2 + 24, 200, 20,
				CommonComponents.GUI_DONE, button -> onClose()));
		//?}

	}

	/**
	 * Called when the screen is rendered.
	 *
	 * @param guiGraphics Graphics drawing class
	 * @param mouseX Mouse X position
	 * @param mouseY Mouse Y position
	 * @param partialTick Partial tick time
	 */
	@Override
	public void render(
			@NotNull /*$ render_input */PoseStack guiGraphics,
			int mouseX,
			int mouseY,
			float partialTick
	) {
		//? if < 1.19.4 {
		renderBackground(guiGraphics);
		//? } elif <= 1.20.2 {
		/*renderDirtBackground(guiGraphics);
		*///? }
		//? if < 26 {
		drawCenteredString(guiGraphics, font, title, width / 2, height / 2 - 60, 0xFFFFFF);
		//? }
		super.render(guiGraphics, mouseX, mouseY, partialTick);
		//? if >= 26 {
		/*int titleWidth = font.width(title);
		guiGraphics.text(font, title, (width - titleWidth) / 2, height / 2 - 60, 0xFFFFFFFF);
		*///? }
	}

	/**
	 * Called when the screen is closed.
	 */
	@Override
	public void onClose() {
		assert minecraft != null;
		//? if >= 26.2 {
		/*minecraft.setScreenAndShow(parent);
		*///?} else {
		minecraft.setScreen(parent);
		//?}
		ConstantMusicManager.SINGLETON.clearSlider();
	}

}
