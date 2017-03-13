package org.limmen.photoarchive;

@FunctionalInterface
public interface ProgressMonitor {
	
	void updateProgress(long current, long total);
}
