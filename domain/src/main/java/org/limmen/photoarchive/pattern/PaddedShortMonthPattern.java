package org.limmen.photoarchive.pattern;

import org.limmen.photoarchive.FileMetadata;

public class PaddedShortMonthPattern extends AbstractPattern {

	@Override
	public boolean applicable(String directory) {
		return testExactPattern(directory, "m", 2);
	}

	@Override
	public String apply(String directory, FileMetadata metadata) {
		String month = rpad(Integer.toString(metadata.getLocalDateTime().getMonthValue()), 2);
		return directory.replace("mm", month);
	}
}
