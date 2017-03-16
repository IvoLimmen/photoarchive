package org.limmen.photoarchive;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileArchiver {

	private final Context context;

	private final CopyOption[] options;

	private final ProgressMonitor progressMonitor;

	private final DateExtractor dateExtractor;
	
	public FileArchiver(Context context, ProgressMonitor progressMonitor) {
		this.context = context;
		this.progressMonitor = progressMonitor;

		if (context.isOverwrite()) {
			options = new CopyOption[]{StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING};
		} else {
			options = new CopyOption[]{StandardCopyOption.COPY_ATTRIBUTES};
		}
		dateExtractor = new DateExtractor(context.isPreferExif());
	}

	public void archive() throws IOException {

		Path sourcePath = context.getSourcePath().toPath();

		copy(Files.list(sourcePath)
			// if the file does not start with a .
			.filter(path -> !path.startsWith("."))
			// only if the extention is in our list
			.filter(path -> context.getExtentions().contains(getExtention(path)))
			.collect(Collectors.toList()));
	}

	private String getExtention(Path file) {
		String ext = file.getFileName().toString();
		return ext.substring(1 + ext.indexOf(".")).toLowerCase();
	}

	private void copy(List<Path> files) {

		// make sure the base directories have been made
		context.getTargetPath().mkdirs();

		for (int index = 0; index < files.size(); index++) {
			Path file = files.get(index);
			progressMonitor.updateProgress(index, files.size());
			copy(file);
		}
	}

	private void copy(Path f) {
		try {
			Files.copy(f, buildTargetPath(f), options);
		}
		catch (IOException ex) {
			System.out.println("Failed: " + f);
			ex.printStackTrace();
		}
	}

	private Path buildTargetPath(Path file) throws IOException {
		LocalDateTime ldt = dateExtractor.getCreationDate(file);
		
		List<String> dirs = new ArrayList<>();
		dirs.add(Integer.toString(ldt.getYear()));
		dirs.add(rpad(Integer.toString(ldt.getMonthValue()), 2));
		dirs.add(rpad(Integer.toString(ldt.getDayOfMonth()), 2));

		Path targetPath = Paths.get(context.getTargetPath().getPath(), dirs.toArray(new String[3]));

		targetPath.toFile().mkdirs();

		return Paths.get(targetPath.toString(), file.getFileName().toString());
	}

	private String rpad(String name, int length) {
		while (name.length() < length) {
			name = "0".concat(name);
		}

		return name;
	}
}
