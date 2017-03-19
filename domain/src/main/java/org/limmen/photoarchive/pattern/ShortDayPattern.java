package org.limmen.photoarchive.pattern;

import java.time.LocalDateTime;

public class ShortDayPattern extends AbstractPattern {

	@Override
	public boolean applicable(String directory) {
		return testExactPattern(directory, "d", 1);
	}

	@Override
	public String apply(String directory, LocalDateTime localDateTime) {
		int day = localDateTime.getDayOfMonth();
		return directory.replace("d", Integer.toString(day));
	}

}
