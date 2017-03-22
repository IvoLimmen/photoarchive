package org.limmen.photoarchive.pattern;

import org.limmen.photoarchive.FileMetadata;

public class ShortDayPattern extends AbstractPattern {

	@Override
	public boolean applicable(String directory) {
		return testExactPattern(directory, "d", 1);
	}

	@Override
	public String apply(String directory, FileMetadata metadata) {
		int day = metadata.getLocalDateTime().getDayOfMonth();
		return directory.replace("d", Integer.toString(day));
	}

}
