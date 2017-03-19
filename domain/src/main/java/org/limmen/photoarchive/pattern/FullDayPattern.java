package org.limmen.photoarchive.pattern;

import java.time.LocalDateTime;

public class FullDayPattern extends AbstractPattern {

	@Override
	public boolean applicable(String directory) {
		return testExactPattern(directory, "d", 3);
	}

	@Override
	public String apply(String directory, LocalDateTime localDateTime) {
		String day = Integer.toString(localDateTime.getDayOfYear());
		return directory.replace("ddd", day);
	}
}
