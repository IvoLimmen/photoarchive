package org.limmen.photoarchive.pattern;

import java.time.LocalDateTime;

public class PaddedShortDayPattern extends AbstractPattern {

	@Override
	public boolean applicable(String directory) {
		return testExactPattern(directory, "d", 2);
	}

	@Override
	public String apply(String directory, LocalDateTime localDateTime) {
		String day = rpad(Integer.toString(localDateTime.getDayOfMonth()), 2);
		return directory.replace("dd", day);
	}
}
