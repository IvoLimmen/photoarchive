package org.limmen.photoarchive.pattern;

import org.limmen.photoarchive.FileMetadata;

public class ShortYearPattern extends AbstractPattern {

	@Override
	public boolean applicable(String directory) {
		return testExactPattern(directory, "y", 2);
	}
	
	@Override
	public String apply(String directory, FileMetadata metadata) {
		int year = metadata.getLocalDateTime().getYear() - 2000;
		return directory.replace("yy", Integer.toString(year));
	}
}
