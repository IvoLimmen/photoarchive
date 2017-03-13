package org.limmen.photoarchive;

import java.io.File;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class Main extends Application {

	private final Context context = new Context();

	private final DirectoryChooser directoryChooser = new DirectoryChooser();

	private Stage stage;

	private final TextField sourceDirectory = new TextField();

	private final TextField targetDirectory = new TextField();

	private final CheckBox overwrite = new CheckBox("Overwrite files");

	public Main() {
		this.context.addExtention("jpg");
		this.context.addExtention("jpeg");
		this.context.addExtention("mp4");
		this.context.setSourcePath(new File(System.getProperty("user.home"), "Downloads"));
		this.context.setTargetPath(new File(System.getProperty("user.home"), "Pictures"));

		this.overwrite.selectedProperty().setValue(context.isOverwrite());
		this.sourceDirectory.setText(this.context.getSourcePath().getAbsolutePath());
		this.targetDirectory.setText(this.context.getTargetPath().getAbsolutePath());
	}

	@Override
	public void start(Stage primaryStage) {
		this.stage = primaryStage;
//      primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/1f4d1.png")));
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

		Label targetDirectoryLabel = new Label("Target directory");
		gridPane.add(targetDirectoryLabel, 0, 1);

		targetDirectory.setMinWidth(250);
		gridPane.add(targetDirectory, 1, 1);

		Button targetDirectoryButton = new Button("...");
		targetDirectoryButton.setOnAction(this::onClickTargetDirectoryButton);
		gridPane.add(targetDirectoryButton, 2, 1);

		gridPane.add(overwrite, 1, 2);

		HBox buttonPanel = new HBox(10);
		buttonPanel.setAlignment(Pos.CENTER);
		gridPane.add(buttonPanel, 0, 3);
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

		return new Scene(root, 430, 150);
	}

	private void onClickExit(ActionEvent event) {
		System.exit(0);
	}

	private void onClickSourceDirectoryButton(ActionEvent event) {
		directoryChooser.setInitialDirectory(context.getSourcePath());
		directoryChooser.setTitle("Select a source directory");
		context.setSourcePath(directoryChooser.showDialog(stage));
		sourceDirectory.setText(context.getSourcePath().getAbsolutePath());
	}

	private void onClickTargetDirectoryButton(ActionEvent event) {
		directoryChooser.setInitialDirectory(context.getTargetPath());
		directoryChooser.setTitle("Select a target directory");
		context.setTargetPath(directoryChooser.showDialog(stage));
		targetDirectory.setText(context.getTargetPath().getAbsolutePath());
	}

	private void onClickStart(ActionEvent event) {
		context.setSourcePath(new File("/home/ivo/Downloads/drive-download"));
		context.setTargetPath(new File("/home/ivo/Downloads/test"));

		context.addExtention("jpg");
		context.addExtention("jpeg");
		context.addExtention("png");
		context.addExtention("mp4");

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
