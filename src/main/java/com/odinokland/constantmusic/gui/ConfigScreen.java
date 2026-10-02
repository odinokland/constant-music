package com.odinokland.constantmusic.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.odinokland.constantmusic.ConstantMusic;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

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
		super(Component.translatable("constantmusic.title"));
		this.parent = parent;
	}

	/**
	 * Called when the screen is initialized.
	 */
	@Override
	protected void init() {
		addRenderableWidget(this.addRenderableWidget(new MusicDelaySlider(
				this.minecraft,
				width / 2 - 100,
				height / 2 - 24,
				200,
				20
		)));
		//? if >=1.19.3 {
		//addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> onClose()).bounds(width / 2 - 100, height - 27, 200, 20).build());
		//?} else {
		addRenderableWidget(new Button(width / 2 - 100, height / 2 + 4, 200, 20,
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
		//? if <= 1.20.1 {
		renderBackground(guiGraphics);
		//? }
		//? < 26 {
		drawCenteredString(guiGraphics, font, title, width / 2, height / 2 - 60, 0xFFFFFF);
		//? }
		super.render(guiGraphics, mouseX, mouseY, partialTick);
		//? >= 26 {
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
		//minecraft.setScreenAndShow(parent);
		//?} else {
		minecraft.setScreen(parent);
		//?}
	}


	/**
	 * Gets a config option.
	 *
	 * @return the config option
	 */
	public static OptionInstance<Integer> getConfigOption() {
		return new OptionInstance<Integer>("constantmusic.option", OptionInstance.noTooltip(), (component, integer) -> {
			return integer.equals(0) ? Component.translatable("options.generic_value", new Object[]{component, CommonComponents.OPTION_OFF}) : ConstantMusic.timeDisplayText(integer);
		}, new OptionInstance.IntRange(0, 600), ConstantMusic.getTimer(), (integer) -> {
			ConstantMusic.setTimer(Integer.parseInt(integer.toString()));
		});
	}
}
