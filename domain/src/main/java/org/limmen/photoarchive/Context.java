package org.limmen.photoarchive;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Context {

	private final List<String> extentions = new ArrayList<>();
	
	private boolean overwrite;

	private File sourcePath;

	private File targetPath;

	public void addExtention(String extention) {
		this.extentions.add(extention);
	}

	public List<String> getExtentions() {
		return extentions;
	}

	public File getSourcePath() {
		return sourcePath;
	}

	public File getTargetPath() {
		return targetPath;
	}

	public boolean isOverwrite() {
		return overwrite;
	}

	public void setOverwrite(boolean overwrite) {
		this.overwrite = overwrite;
	}

	public void setSourcePath(File sourcePath) {
		this.sourcePath = sourcePath;
	}

	public void setTargetPath(File targetPath) {
		this.targetPath = targetPath;
	}
}
