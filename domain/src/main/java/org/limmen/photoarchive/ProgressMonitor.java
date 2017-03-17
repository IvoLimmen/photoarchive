package org.limmen.photoarchive;

import java.nio.file.Path;

@FunctionalInterface
public interface ProgressMonitor {
	
	void updateProgress(Path currentFile, long failed, long skipped, long current, long total);
}
