package org.limmen.photoarchive;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ProgressDialog {
   
   private final Stage dialogStage;
   private final ProgressBar progressBar = new ProgressBar(0);
   private final Label label = new Label();
   
   public ProgressDialog(String title) {
      dialogStage = new Stage();
      dialogStage.initStyle(StageStyle.UTILITY);
      dialogStage.setResizable(false);
      dialogStage.initModality(Modality.APPLICATION_MODAL);
      dialogStage.setTitle(title);
      
      progressBar.setProgress(0);  
      progressBar.setPrefSize(300d, 15d);
      
      final VBox vb = new VBox();
      vb.setSpacing(5);
      vb.setAlignment(Pos.CENTER);
	  
	  vb.getChildren().add(label);
      vb.getChildren().add(progressBar);      
      
      Scene scene = new Scene(vb, 400, 45);
      dialogStage.setScene(scene);
   }
   
   public void activateProgressBar(final TaskExecutor task) {
      progressBar.progressProperty().bind(task.progressProperty());
	  label.textProperty().bind(task.getFileProperty());
   }
   
   public Stage getDialogStage() {
      return dialogStage;
   }
}
