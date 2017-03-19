package org.limmen.photoarchive.pattern;

import java.time.LocalDateTime;

public class PaddedFullDayPattern extends AbstractPattern {

	@Override
	public boolean applicable(String directory) {
		return testExactPattern(directory, "d", 4);
	}

	@Override
	public String apply(String directory, LocalDateTime localDateTime) {
		String day = rpad(Integer.toString(localDateTime.getDayOfYear()), 3);
		return directory.replace("dddd", day);
	}
}
