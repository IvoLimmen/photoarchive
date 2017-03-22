package org.limmen.photoarchive.pattern;

import java.time.format.TextStyle;
import java.util.Locale;
import org.limmen.photoarchive.FileMetadata;

public class NamedMonthPattern extends AbstractPattern {

	@Override
	public boolean applicable(String directory) {
		return testExactPattern(directory, "m", 3);
	}

	@Override
	public String apply(String directory, FileMetadata metadata) {
		String month = metadata.getLocalDateTime().getMonth().getDisplayName(TextStyle.FULL, Locale.US);
		return directory.replace("mmm", month);
	}
}
