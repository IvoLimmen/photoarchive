package org.limmen.photoarchive;

import java.nio.file.Path;

public interface ProgressMonitor {
	
	void skipFile(Path file);

	void failedFile(Path file, Throwable throwable);
	
	void updateProgress(Path currentFile, long current, long total);
}
