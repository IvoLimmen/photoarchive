package org.limmen.photoarchive;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TargetBuilder {

	private String pattern;

	private final DateExtractor dateExtractor;

	private final Path targetPath;
	
	public TargetBuilder(Context context) {
		this.dateExtractor = new DateExtractor(context.isPreferExif());
		this.pattern = context.getTargetPattern();
		this.targetPath = context.getTargetPath().toPath();
	}

	public Path createTarget(Path file) throws IOException {
		LocalDateTime date = dateExtractor.getCreationDate(file);

		List<String> dirs = new ArrayList<>();
		dirs.add(Integer.toString(date.getYear()));
		dirs.add(rpad(Integer.toString(date.getMonthValue()), 2));
		dirs.add(rpad(Integer.toString(date.getDayOfMonth()), 2));

		Path target = Paths.get(this.targetPath.toString(), dirs.toArray(new String[3]));

		target.toFile().mkdirs();

		return Paths.get(target.toString(), file.getFileName().toString());
	}

	private String rpad(String name, int length) {
		while (name.length() < length) {
			name = "0".concat(name);
		}

		return name;
	}
}
