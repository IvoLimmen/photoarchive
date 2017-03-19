package org.limmen.photoarchive.pattern;

import java.time.LocalDateTime;

public class ShortYearPattern extends AbstractPattern {

	@Override
	public boolean applicable(String directory) {
		return testExactPattern(directory, "y", 2);
	}
	
	@Override
	public String apply(String directory, LocalDateTime localDateTime) {
		int year = localDateTime.getYear() - 2000;
		return directory.replace("yy", Integer.toString(year));
	}
}
