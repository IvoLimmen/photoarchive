package org.limmen.photoarchive;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main2 extends Application {

   @Override
   public void start(Stage primaryStage) {
//      primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/1f4d1.png")));
      primaryStage.setTitle("PhotoArchive");
      primaryStage.setScene(mainScene());
      primaryStage.setResizable(true);
      primaryStage.show();
   }

   public static void main(String[] args) {
      launch(args);
   }

   private Scene mainScene() {
      StackPane root = new StackPane();
//      root.getChildren().add(vbox);

      return new Scene(root, 1000, 600);
   }

   private void showError(String title, String error) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Error");
      alert.setHeaderText(title);
      alert.setContentText(error);

      alert.showAndWait();
   }
}
