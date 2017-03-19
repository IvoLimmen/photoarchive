package org.limmen.photoarchive.pattern;

import java.time.LocalDateTime;

public abstract class AbstractPattern {

	public abstract boolean applicable(String directory);

	public abstract String apply(String directory, LocalDateTime localDateTime);

	protected boolean testExactPattern(String directory, String character, int amount) {
		boolean applicable = false;

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < amount; i++) {
			sb.append(character);
		}
		String test = sb.toString();
		
		int index = directory.indexOf(test);

		if (index >= 0) {
			applicable = true;

			if (directory.contains(test.concat(character))) {
				applicable = false;
			}
		}

		return applicable;
	}

	protected String rpad(String name, int length) {
		while (name.length() < length) {
			name = "0".concat(name);
		}

		return name;
	}
}
