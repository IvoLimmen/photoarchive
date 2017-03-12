package org.limmen.photoarchive;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileArchiver {

	private final Context context;

	private final CopyOption[] options;

	public FileArchiver(Context context) {
		this.context = context;

		if (context.isOverwrite()) {
			options = new CopyOption[]{StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING};
		} else {
			options = new CopyOption[]{StandardCopyOption.COPY_ATTRIBUTES};
		}
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

		files.forEach(f -> {
			copy(f);
		});
	}

	private void copy(Path f) {
		System.out.println("Copy " + f);

		try {
			Files.copy(f, buildTargetPath(f), options);
		}
		catch (IOException ex) {
			System.out.println("Failed: " + f);
			ex.printStackTrace();
		}
	}

	private Path buildTargetPath(Path file) throws IOException {
		BasicFileAttributes bfa = Files.readAttributes(file, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
		LocalDateTime ldt = LocalDateTime.ofInstant(bfa.creationTime().toInstant(), ZoneOffset.UTC);

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
