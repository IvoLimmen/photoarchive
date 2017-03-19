package org.limmen.photoarchive.pattern;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
	}

	public String[] apply(LocalDateTime localDateTime) {
		List<String> dirs = new ArrayList<>();

		String[] parts = pattern.split("\\\\");
		for (String part : parts) {
			dirs.add(applyDateToDir(part, localDateTime));
		}

		return dirs.toArray(new String[dirs.size()]);
	}

	private String applyDateToDir(String directory, LocalDateTime localDateTime) {
		for (int i = 0; i < directory.length(); i++) {
			for (AbstractPattern abstractPattern : this.patterns) {
				if (abstractPattern.applicable(directory)) {
					directory = abstractPattern.apply(directory, localDateTime);
				}
			}
		}
		
		return directory;
	}
}
