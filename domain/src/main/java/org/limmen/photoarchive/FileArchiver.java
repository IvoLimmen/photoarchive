package org.limmen.photoarchive;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

public class FileArchiver {

	private final Context context;

	private final CopyOption[] options;

	private final ProgressMonitor progressMonitor;

	private final TargetBuilder targetBuilder;
	
	public FileArchiver(Context context, ProgressMonitor progressMonitor) {
		this.context = context;
		this.progressMonitor = progressMonitor;
		this.targetBuilder = new TargetBuilder(context);
		if (context.isOverwrite()) {
			options = new CopyOption[]{StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING};
		} else {
			options = new CopyOption[]{StandardCopyOption.COPY_ATTRIBUTES};
		}
	}

	public void archive() throws IOException {

		Path sourcePath = context.getSourcePath().toPath();

		copy(Files.list(sourcePath).collect(Collectors.toList()));
	}

	private String getExtention(Path file) {
		String ext = file.getFileName().toString();
		return ext.substring(1 + ext.lastIndexOf(".")).toLowerCase();
	}

	private void copy(List<Path> files) {

		// make sure the base directories have been made
		context.getTargetPath().mkdirs();

		for (int index = 0; index < files.size(); index++) {
			Path file = files.get(index);

			if (!context.getExtentions().contains(getExtention(file))) {
				progressMonitor.skipFile(file);
			} else {
				progressMonitor.updateProgress(file, index, files.size());
				try {
					copy(file);
				}
				catch (IOException ex) {
					progressMonitor.failedFile(file, ex);
				}
			}
		}
	}

	private void copy(Path f) throws IOException {
		Files.copy(f, targetBuilder.createTarget(f), options);
	}
}
