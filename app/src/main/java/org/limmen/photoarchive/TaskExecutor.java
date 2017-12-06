package org.limmen.photoarchive;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;

public class TaskExecutor extends Task<Void> implements ProgressMonitor {

	private final List<String> failedList = new ArrayList<>();

	private final SimpleLongProperty failedProperty = new SimpleLongProperty();

	private final FileArchiver fileArchiver;

	private final SimpleStringProperty fileProperty = new SimpleStringProperty();

	private final List<String> skippedList = new ArrayList<>();

	private final SimpleLongProperty skippedProperty = new SimpleLongProperty();

	public TaskExecutor(Context context) {
		failedList.clear();
		skippedList.clear();

		this.fileArchiver = new FileArchiver(context, this);
	}

	@Override
	public void failedFile(Path file, Throwable throwable) {
		Platform.runLater(() -> {
			failedProperty.set(failedProperty.get() + 1);
			failedList.add(file.toString());
         System.out.println("FAILED:" + file.getFileName());	
         throwable.printStackTrace();
	});
	}

	public List<String> getFailedList() {
		return failedList;
	}

	public ReadOnlyLongProperty getFailedProperty() {
		return failedProperty;
	}

	public ReadOnlyStringProperty getFileProperty() {
		return fileProperty;
	}

	public List<String> getSkippedList() {
		return skippedList;
	}

	public ReadOnlyLongProperty getSkippedProperty() {
		return skippedProperty;
	}

	@Override
	public void skipFile(Path file) {
		Platform.runLater(() -> {
			skippedProperty.set(skippedProperty.get() + 1);
			skippedList.add(file.toString());
		});
	}

	@Override
	public void updateProgress(Path currentFile, long current, long total) {
		Platform.runLater(() -> {
			this.fileProperty.set(currentFile.getFileName().toString());
		});
		this.updateProgress(current, total);
	}

	@Override
	protected Void call() throws Exception {
		fileArchiver.archive();
		return null;
	}

}
