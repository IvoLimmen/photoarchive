package org.limmen.photoarchive.pattern;

import java.util.ArrayList;
import java.util.List;
import org.limmen.photoarchive.FileMetadata;

public class PatternApplier {

	private final String pattern;

	private final List<AbstractPattern> patterns = new ArrayList<>();

	public PatternApplier(String pattern) {
		this.pattern = pattern;
		patterns.add(new YearPattern());
		patterns.add(new ShortYearPattern());
		patterns.add(new ShortMonthPattern());
		patterns.add(new ShortDayPattern());
		patterns.add(new PaddedFullDayPattern());
		patterns.add(new PaddedShortDayPattern());
		patterns.add(new PaddedShortMonthPattern());
		patterns.add(new NamedMonthPattern());
		patterns.add(new FullDayPattern());
		patterns.add(new CountryCodePattern());
	}

	public String[] apply(FileMetadata fileMetadata) {
		List<String> dirs = new ArrayList<>();

		String[] parts = pattern.split("\\\\");
		for (String part : parts) {
			dirs.add(applyDateToDir(part, fileMetadata));
		}

		return dirs.toArray(new String[dirs.size()]);
	}

	private String applyDateToDir(final String directory, final FileMetadata localDateTime) {
		String newDirectory = directory;
		for (AbstractPattern abstractPattern : this.patterns) {
			if (abstractPattern.applicable(newDirectory)) {
				newDirectory = abstractPattern.apply(newDirectory, localDateTime);
			}
		}

		return newDirectory;
	}
}
