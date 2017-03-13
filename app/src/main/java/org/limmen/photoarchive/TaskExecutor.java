package org.limmen.photoarchive;

import javafx.concurrent.Task;

public class TaskExecutor extends Task<Void> {

	private final FileArchiver fileArchiver;

	public TaskExecutor(Context context) {
		this.fileArchiver = new FileArchiver(context, (current, total) -> {
			this.updateProgress(current, total);
		});
	}

	@Override
	protected Void call() throws Exception {
		fileArchiver.archive();
		return null;
	}

}
