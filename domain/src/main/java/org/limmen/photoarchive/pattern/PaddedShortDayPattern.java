package org.limmen.photoarchive.pattern;

import org.limmen.photoarchive.FileMetadata;

public class PaddedShortDayPattern extends AbstractPattern {

	@Override
	public boolean applicable(String directory) {
		return testExactPattern(directory, "d", 2);
	}

	@Override
	public String apply(String directory, FileMetadata metadata) {
		String day = rpad(Integer.toString(metadata.getLocalDateTime().getDayOfMonth()), 2);
		return directory.replace("dd", day);
	}
}
