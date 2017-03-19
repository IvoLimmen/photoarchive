package org.limmen.photoarchive.pattern;

import java.time.LocalDateTime;

public class PaddedShortMonthPattern extends AbstractPattern {

	@Override
	public boolean applicable(String directory) {
		return testExactPattern(directory, "m", 2);
	}

	@Override
	public String apply(String directory, LocalDateTime localDateTime) {
		String month = rpad(Integer.toString(localDateTime.getMonthValue()), 2);
		return directory.replace("mm", month);
	}
}
