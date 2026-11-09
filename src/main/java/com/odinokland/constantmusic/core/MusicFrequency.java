package com.odinokland.constantmusic.core;

import com.mojang.serialization.Codec;
import com.odinokland.constantmusic.util.Constants;
import com.odinokland.constantmusic.util.TranslatableComponentHelper;
import net.minecraft.sounds.Music;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;
import net.minecraft.network.chat.Component;
//? if >= 1.19.0 {
import net.minecraft.util.RandomSource;
//? } else {
/*import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
*///? }

/**
 * The enum Music frequency.
 */
public enum MusicFrequency implements StringRepresentable {
	/**
	 * Default music frequency.
	 */
	DEFAULT("DEFAULT", Constants.MUSIC_DEFAULT, 20),
	/**
	 * Frequent music frequency.
	 */
	FREQUENT("FREQUENT", Constants.MUSIC_FREQUENT, 10),
	/**
	 * Fixed music frequency.
	 */
	FIXED("FIXED", Constants.MUSIC_FIXED, 0),
	/**
	 * Constant music frequency.
	 */
	CONSTANT("CONSTANT", Constants.MUSIC_CONSTANT, 0);

	/**
	 * The constant CODEC.
	 */
	//? if >= 1.19.0 {
	public static final Codec<MusicFrequency> CODEC = StringRepresentable.fromEnum(MusicFrequency::values);
	//? } else {
	/*public static final Codec<MusicFrequency> CODEC = StringRepresentable.fromEnum(MusicFrequency::values, MusicFrequency::byName);
	private static final Map<String, MusicFrequency> BY_NAME = (Map<String, MusicFrequency>) Arrays.stream(values())
			.collect(Collectors.toMap(MusicFrequency::getSerializedName, musicFrequency -> musicFrequency));
	*///? }
	private final String name;
	private final int maxFrequency;
	private final Component caption;

	/**
	 * Enum constructor
	 * @param name Selection name
	 * @param translationKey Translation key
	 * @param maxFrequencyMinutes Maximum frequency in minutes
	 */
	MusicFrequency(final String name, final String translationKey, final int maxFrequencyMinutes) {
		this.name = name;
		this.maxFrequency = maxFrequencyMinutes * 1200;
		//? if >= 1.19.0 {
		this.caption = Component.translatable(translationKey);
		//? } else {
		/*this.caption = TranslatableComponentHelper.translatable(translationKey);
		*///? }
	}

	private int getMaxFrequency() {
		if (this == FIXED) {
			return ConstantMusicManager.SINGLETON.getTimer() * 20;
		}
		return this.maxFrequency;
	}

	/**
	 * Gets next song delay.
	 *
	 * @param music  the music
	 * @param random the random
	 * @return the next song delay
	 */
	public int getNextSongDelay(final Music music, final /*? if >= 1.19.0 {*/RandomSource/*?} else {*/ /*Random *//*?}*/ random) {
		int maxAllowed = this.getMaxFrequency();
		if (music == null) {
			return maxAllowed;
		} else if (this == CONSTANT) {
			return 100;
		} else {
			//? if > 1.21.5 {
			/*int musicMinDelay = music.minDelay();
			int musicMaxDelay = music.maxDelay();
			*///? } else {
			int musicMinDelay = music.getMinDelay();
			int musicMaxDelay = music.getMaxDelay();
			//? }
			int minFrequency = Math.min(musicMinDelay, maxAllowed);
			int maxFrequency = Math.min(musicMaxDelay, maxAllowed);
			return Mth.nextInt(random, minFrequency, maxFrequency);
		}
	}

	/**
	 * Caption component.
	 *
	 * @return the component
	 */
	//? if >= 1.19.0 {
	public Component caption() {
		return this.caption;
	}
	//? } else {
	/*public Component caption() { return this.caption; }

	/^*
	 * Get by name
	 * @param name name to query
	 * @return MusicFrequency enum
	 ^/
	public static MusicFrequency byName(String name) {
		return (MusicFrequency)BY_NAME.get(name);
	}
	*///? }

	/**
	 * Gets enum name.
	 *
	 * @return the enum name
	 */
	@NotNull
	@Override
	public String getSerializedName() {
		return this.name;
	}
}
