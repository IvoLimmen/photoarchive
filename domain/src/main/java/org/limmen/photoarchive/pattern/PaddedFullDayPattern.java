package org.limmen.photoarchive.pattern;

import org.limmen.photoarchive.FileMetadata;

public class PaddedFullDayPattern extends AbstractPattern {

	@Override
	public boolean applicable(String directory) {
		return testExactPattern(directory, "d", 4);
	}

	@Override
	public String apply(String directory, FileMetadata metadata) {
		String day = rpad(Integer.toString(metadata.getLocalDateTime().getDayOfYear()), 3);
		return directory.replace("dddd", day);
	}
}
