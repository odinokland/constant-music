package com.moandjiezana.toml;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * The interface Value reader.
 */
interface ValueReader {

	/**
	 * Can read boolean.
	 *
	 * @param s must already have been trimmed
	 * @return the boolean
	 */
	boolean canRead(String s);

	/**
	 * Partial validation. Stops after type terminator, rather than at EOI.
	 *
	 * @param s       must already have been validated by {@link #canRead(String)}
	 * @param index   where to start in s
	 * @param context the context
	 * @return a value or a {@link Results.Errors}
	 */
	Object read(String s, AtomicInteger index, Context context);
}
