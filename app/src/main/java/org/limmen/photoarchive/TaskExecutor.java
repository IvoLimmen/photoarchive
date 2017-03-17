package org.limmen.photoarchive;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;

public class TaskExecutor extends Task<Void> {

	private final SimpleLongProperty failedProperty = new SimpleLongProperty();
	private final FileArchiver fileArchiver;

	private final SimpleStringProperty fileProperty = new SimpleStringProperty();
	private final SimpleLongProperty skippedProperty = new SimpleLongProperty();

	public TaskExecutor(Context context) {
		this.fileArchiver = new FileArchiver(context, (file, failed, skipped, current, total) -> {
			Platform.runLater(() -> {
				fileProperty.set(file.getFileName().toString());
				failedProperty.set(failed);
				skippedProperty.set(skipped);
			});
			this.updateProgress(current, total);
		});
	}

	public ReadOnlyLongProperty getFailedProperty() {
		return failedProperty;
	}

	public ReadOnlyStringProperty getFileProperty() {
		return fileProperty;
	}

	public ReadOnlyLongProperty getSkippedProperty() {
		return skippedProperty;
	}

	@Override
	protected Void call() throws Exception {
		fileArchiver.archive();
		return null;
	}

}
