package org.limmen.photoarchive.pattern;

import org.limmen.photoarchive.FileMetadata;

public class YearPattern extends AbstractPattern {

	@Override
	public boolean applicable(String directory) {
		return testExactPattern(directory, "y", 4);
	}
	
	@Override
	public String apply(String directory, FileMetadata metadata) {
		int year = metadata.getLocalDateTime().getYear();
		return directory.replace("yyyy", Integer.toString(year));
	}
}
