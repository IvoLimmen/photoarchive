package org.limmen.photoarchive;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class Main extends Application {

	private final Context context = Settings.getInitialContext();

	private final DirectoryChooser directoryChooser = new DirectoryChooser();

	private Stage stage;

	private final TextField sourceDirectory = new TextField();

	private final TextField extentions = new TextField();

	private final TextField targetDirectory = new TextField();

	private final TextField targetPathPattern = new TextField();
	
	private final CheckBox overwrite = new CheckBox("Overwrite files");

	private final CheckBox preferExif = new CheckBox("Prefer EXIF");

	public Main() {
		this.overwrite.selectedProperty().setValue(context.isOverwrite());
		this.preferExif.selectedProperty().setValue(context.isPreferExif());
		this.extentions.setText(context.getExtentions().stream().collect(Collectors.joining(",")));
		this.targetPathPattern.setText(context.getTargetPattern());
		this.sourceDirectory.setText(this.context.getSourcePath().getAbsolutePath());
		this.targetDirectory.setText(this.context.getTargetPath().getAbsolutePath());
	}

	@Override
	public void start(Stage primaryStage) {
		this.stage = primaryStage;
		primaryStage.setTitle("PhotoArchive");
		primaryStage.setScene(mainScene());
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	private Scene mainScene() {
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(5));

		Label sourceDirectoryLabel = new Label("Source directory");
		gridPane.add(sourceDirectoryLabel, 0, 0);

		sourceDirectory.setMinWidth(250);
		gridPane.add(sourceDirectory, 1, 0);

		Button sourceDirectoryButton = new Button("...");
		sourceDirectoryButton.setOnAction(this::onClickSourceDirectoryButton);
		gridPane.add(sourceDirectoryButton, 2, 0);

		Label extentionsLabel = new Label("Extentions");
		gridPane.add(extentionsLabel, 0, 1);

		extentions.setMaxWidth(250);
		gridPane.add(extentions, 1, 1);

		Label targetDirectoryLabel = new Label("Target directory");
		gridPane.add(targetDirectoryLabel, 0, 2);

		targetDirectory.setMinWidth(250);
		gridPane.add(targetDirectory, 1, 2);

		Button targetDirectoryButton = new Button("...");
		targetDirectoryButton.setOnAction(this::onClickTargetDirectoryButton);
		gridPane.add(targetDirectoryButton, 2, 2);
		
		Label targetPatternLabel = new Label("Target pattern");
		gridPane.add(targetPatternLabel, 0, 3);

		targetPathPattern.setTooltip(new Tooltip("Use the following codes:\nyyyy - The full year\nyy - The short year\n"
			+ "mmm - Month in text\nmm - Month in numbers padded to fit\nm - Month in numbers\n"
			+ "ddd - Days of year\ndd - Days of month padded to fit\nd - Days of month\n"
			+ "cc - CountryCode from GPS location"));
		targetPathPattern.setMinWidth(250);
		gridPane.add(targetPathPattern, 1, 3);
		
		gridPane.add(overwrite, 1, 4);
		gridPane.add(preferExif, 1, 5);

		HBox buttonPanel = new HBox(10);
		buttonPanel.setAlignment(Pos.CENTER);
		gridPane.add(buttonPanel, 0, 6);
		GridPane.setColumnSpan(buttonPanel, GridPane.REMAINING);

		Button startButton = new Button("Execute");
		startButton.setMinWidth(150);
		startButton.setOnAction(this::onClickStart);
		buttonPanel.getChildren().add(startButton);

		Button exitButton = new Button("Exit");
		exitButton.setMinWidth(150);
		exitButton.setOnAction(this::onClickExit);
		buttonPanel.getChildren().add(exitButton);

		StackPane root = new StackPane();
		root.getChildren().add(gridPane);

		return new Scene(root, 430, 230);
	}

	private void onClickExit(ActionEvent event) {
		Settings.setInitialContext(context);
		System.exit(0);
	}

	private void onClickSourceDirectoryButton(ActionEvent event) {
		directoryChooser.setInitialDirectory(context.getSourcePath());
		directoryChooser.setTitle("Select a source directory");
		File tmp = directoryChooser.showDialog(stage);
		if (tmp != null) {
			context.setSourcePath(tmp);
			sourceDirectory.setText(context.getSourcePath().getAbsolutePath());
		}
	}

	private void onClickTargetDirectoryButton(ActionEvent event) {
		directoryChooser.setInitialDirectory(context.getTargetPath());
		directoryChooser.setTitle("Select a target directory");
		File tmp = directoryChooser.showDialog(stage);
		if (tmp != null) {
			context.setTargetPath(tmp);
			targetDirectory.setText(context.getTargetPath().getAbsolutePath());
		}
	}

	private void onClickStart(ActionEvent event) {
		context.setSourcePath(new File(sourceDirectory.getText()));
		context.setTargetPath(new File(targetDirectory.getText()));
		context.setTargetPattern(targetPathPattern.getText());
		context.getExtentions().clear();
		if (extentions.getText() != null && extentions.getText().length() > 0) {
			context.getExtentions().addAll(Arrays.asList(extentions.getText().toLowerCase().split(",")));
		}
		context.setOverwrite(overwrite.selectedProperty().getValue());
		context.setPreferExif(preferExif.selectedProperty().getValue());

		// checks
		if (context.getExtentions().isEmpty()) {
			showError("Error", "There are no extentions, nothing would be copied...");
			return;
		}

		TaskExecutor taskExecutor = new TaskExecutor(context);
		ProgressDialog dialog = new ProgressDialog("Copying...");

		// binds progress of progress bars to progress of task:
		dialog.activateProgressBar(taskExecutor);

		// in real life this method would get the result of the task
		// and update the UI based on its value:
		taskExecutor.setOnSucceeded(e -> {
			dialog.getDialogStage().close();
		});

		dialog.getDialogStage().show();

		Thread thread = new Thread(taskExecutor);
		thread.start();
	}

	private void showError(String title, String error) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(title);
		alert.setContentText(error);

		alert.showAndWait();
	}
}
