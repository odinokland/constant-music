package com.moandjiezana.toml;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * The type Resource location converter.
 */
class ResourceLocationConverter {

	/**
	 * The ResourceLocation converter.
	 */
	static final ResourceLocationConverter IDENTIFIER_CONVERTER = new ResourceLocationConverter();

	/**
	 * Convert resource location.
	 *
	 * @param s       the s
	 * @param index   the index
	 * @param context the context
	 * @return the resource location
	 */
	ResourceLocation convert(String s, AtomicInteger index, Context context) {
		boolean quoted = false;
		StringBuilder name = new StringBuilder();
		boolean terminated = false;
		boolean isKey = s.charAt(index.get()) != '[';
		boolean isTableArray = !isKey && s.length() > index.get() + 1 && s.charAt(index.get() + 1) == '[';
		boolean inComment = false;

		for (int i = index.get(); i < s.length(); i = index.incrementAndGet()) {
			char c = s.charAt(i);
			if (Keys.isQuote(c) && (i == 0 || s.charAt(i - 1) != '\\')) {
				quoted = !quoted;
				name.append(c);
			} else if (c == '\n') {
				index.decrementAndGet();
				break;
			} else if (quoted) {
				name.append(c);
			} else if (c == '=' && isKey) {
				terminated = true;
				break;
			} else if (c == ']' && !isKey) {
				if (!isTableArray || s.length() > index.get() + 1 && s.charAt(index.get() + 1) == ']') {
					terminated = true;
					name.append(']');
					if (isTableArray) {
						name.append(']');
					}
				}
			} else if (terminated && c == '#') {
				inComment = true;
			} else if (terminated && !Character.isWhitespace(c) && !inComment) {
				terminated = false;
				break;
			} else if (!terminated) {
				name.append(c);
			}
		}

		if (!terminated) {
			if (isKey) {
				context.errors.unterminatedKey(name.toString(), context.line.get());
			} else {
				context.errors.invalidKey(name.toString(), context.line.get());
			}

			return ResourceLocation.INVALID;
		}

		return ResourceLocation.from(name.toString(), context);
	}

	private ResourceLocationConverter() {
	}
}
