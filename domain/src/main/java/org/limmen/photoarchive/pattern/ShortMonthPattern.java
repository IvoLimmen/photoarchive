package org.limmen.photoarchive.pattern;

import org.limmen.photoarchive.FileMetadata;

public class ShortMonthPattern extends AbstractPattern {

	@Override
	public boolean applicable(String directory) {
		return testExactPattern(directory, "m", 1);
	}

	@Override
	public String apply(String directory, FileMetadata metadata) {
		int month = metadata.getLocalDateTime().getMonthValue();
		return directory.replace("m", Integer.toString(month));
	}

}
