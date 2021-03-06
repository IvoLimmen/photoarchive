package org.limmen.photoarchive;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.limmen.photoarchive.pattern.PatternApplier;

public class TargetBuilder {

	private final DateExtractor dateExtractor;

	private final LocationExtractor locationExtractor;
	
	private final PatternApplier patternApplier;

	private final Path targetPath;

	public TargetBuilder(Context context) {
		this.dateExtractor = new DateExtractor(context);
		this.locationExtractor = new LocationExtractor(context);
		this.targetPath = context.getTargetPath().toPath();
		this.patternApplier = new PatternApplier(context.getTargetPattern());
	}

	public Path createTarget(Path file) throws IOException {
		FileMetadata fileMetadata = new FileMetadata();
		dateExtractor.extractCreationDate(file, fileMetadata);
		locationExtractor.extractLocation(file, fileMetadata);
					
		Path target = Paths.get(this.targetPath.toString(), patternApplier.apply(fileMetadata));

		target.toFile().mkdirs();

		return Paths.get(target.toString(), file.getFileName().toString());
	}
}
