package org.limmen.photoarchive.pattern;

import java.time.LocalDateTime;

public class YearPattern extends AbstractPattern {

	@Override
	public boolean applicable(String directory) {
		return testExactPattern(directory, "y", 4);
	}
	
	@Override
	public String apply(String directory, LocalDateTime localDateTime) {
		int year = localDateTime.getYear();
		return directory.replace("yyyy", Integer.toString(year));
	}
}
