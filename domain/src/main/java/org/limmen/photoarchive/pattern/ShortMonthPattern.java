package org.limmen.photoarchive.pattern;

import java.time.LocalDateTime;

public class ShortMonthPattern extends AbstractPattern {

	@Override
	public boolean applicable(String directory) {
		return testExactPattern(directory, "m", 1);
	}

	@Override
	public String apply(String directory, LocalDateTime localDateTime) {
		int month = localDateTime.getMonthValue();
		return directory.replace("m", Integer.toString(month));
	}

}
