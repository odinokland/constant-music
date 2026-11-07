package com.odinokland.constantmusic.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
//? < 1.19.0 {
//import net.minecraft.network.chat.TranslatableComponent;
//? }

/**
 * The type Translatable component helper.
 */
public class TranslatableComponentHelper {
	/**
	 * Translatable mutable component.
	 *
	 * @param key the key
	 * @return the mutable component
	 */
	public static MutableComponent translatable(String key) {
		//? >= 1.19.0 {
		return Component.translatable(key);
		//? } else {
		//return new TranslatableComponent(key);
		//? }
	}

	/**
	 * Translatable mutable component.
	 *
	 * @param key    the key
	 * @param params the params
	 * @return the mutable component
	 */
	public static MutableComponent translatable(String key, Object...params) {
		//? >= 1.19.0 {
		return Component.translatable(key, params);
		//? } else {
		//return new TranslatableComponent(key, params);
		//? }
	}
}
