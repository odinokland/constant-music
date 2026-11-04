package com.moandjiezana.toml;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * The type Context.
 */
class Context {
	/**
	 * The ResourceLocation.
	 */
	final ResourceLocation identifier;
	/**
	 * The Line.
	 */
	final AtomicInteger line;
	/**
	 * The Errors.
	 */
	final Results.Errors errors;

	/**
	 * Instantiates a new Context.
	 *
	 * @param identifier the identifier
	 * @param line       the line
	 * @param errors     the errors
	 */
	public Context(ResourceLocation identifier, AtomicInteger line, Results.Errors errors) {
		this.identifier = identifier;
		this.line = line;
		this.errors = errors;
	}

	/**
	 * With context.
	 *
	 * @param identifier the identifier
	 * @return the context
	 */
	public Context with(ResourceLocation identifier) {
		return new Context(identifier, line, errors);
	}
}
