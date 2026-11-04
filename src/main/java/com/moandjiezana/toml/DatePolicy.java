package com.moandjiezana.toml;

import java.util.TimeZone;

/**
 * The type Date policy.
 */
class DatePolicy {

	private final TimeZone timeZone;
	private final boolean showFractionalSeconds;

	/**
	 * Instantiates a new Date policy.
	 *
	 * @param timeZone              the time zone
	 * @param showFractionalSeconds the show fractional seconds
	 */
	DatePolicy(TimeZone timeZone, boolean showFractionalSeconds) {
		this.timeZone = timeZone;
		this.showFractionalSeconds = showFractionalSeconds;
	}

	/**
	 * Gets time zone.
	 *
	 * @return the time zone
	 */
	TimeZone getTimeZone() {
		return timeZone;
	}

	/**
	 * Is show fractional seconds boolean.
	 *
	 * @return the boolean
	 */
	boolean isShowFractionalSeconds() {
		return showFractionalSeconds;
	}
}
