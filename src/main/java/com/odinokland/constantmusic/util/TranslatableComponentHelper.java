package com.odinokland.constantmusic.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
//? < 1.19.0 {
//import net.minecraft.network.chat.TranslatableComponent;
//? }

public class TranslatableComponentHelper {
	public static MutableComponent translatable(String key) {
		//? >= 1.19.0 {
		return Component.translatable(key);
		//? } else {
		//return new TranslatableComponent(key);
		//? }
	}

	public static MutableComponent translatable(String key, Object...params) {
		//? >= 1.19.0 {
		return Component.translatable(key, params);
		//? } else {
		//return new TranslatableComponent(key, params);
		//? }
	}
}
