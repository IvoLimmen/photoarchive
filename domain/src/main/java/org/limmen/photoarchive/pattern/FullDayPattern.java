package org.limmen.photoarchive.pattern;

import org.limmen.photoarchive.FileMetadata;

public class FullDayPattern extends AbstractPattern {

	@Override
	public boolean applicable(String directory) {
		return testExactPattern(directory, "d", 3);
	}

	@Override
	public String apply(String directory, FileMetadata metadata) {
		String day = Integer.toString(metadata.getLocalDateTime().getDayOfYear());
		return directory.replace("ddd", day);
	}
}
