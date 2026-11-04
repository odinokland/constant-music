package com.odinokland.constantmusic.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The type Constants.
 */
public class Constants {
	private Constants() {
	}

	/**
	 * The constant MUSIC_DEFAULT.
	 */
//~ option_translations
	public static final String MUSIC_DEFAULT = "constantmusic.options.music_frequency.default";
	/**
	 * The constant MUSIC_FREQUENT.
	 */
	public static final String MUSIC_FREQUENT = "constantmusic.options.music_frequency.frequent";
	/**
	 * The constant MUSIC_FIXED.
	 */
	public static final String MUSIC_FIXED = "constantmusic.options.music_frequency.fixed";
	/**
	 * The constant MUSIC_CONSTANT.
	 */
	public static final String MUSIC_CONSTANT = "constantmusic.options.music_frequency.constant";

	/**
	 * The constant MOD_ID.
	 */
	public static final String MOD_ID = /*$ mod_id */"constantmusic";
	/**
	 * The constant MOD_NAME.
	 */
	public static final String MOD_NAME = /*$ mod_name */"Constant Music";
	/**
	 * The constant LOG.
	 */
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
}
