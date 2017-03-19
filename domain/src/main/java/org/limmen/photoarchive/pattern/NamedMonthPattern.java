package org.limmen.photoarchive.pattern;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class NamedMonthPattern extends AbstractPattern {

	@Override
	public boolean applicable(String directory) {
		return testExactPattern(directory, "m", 3);
	}

	@Override
	public String apply(String directory, LocalDateTime localDateTime) {
		String month = localDateTime.getMonth().getDisplayName(TextStyle.FULL, Locale.US);
		return directory.replace("mmm", month);
	}
}
