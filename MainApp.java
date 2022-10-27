package org.openjfx;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.StageStyle;



public class MainApp extends Application {

    private FXMLLoader loader = new FXMLLoader();
    private Parent root = null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Library a = new Library();
        if(a.someLibraryMethod()){
            primaryStage.setTitle("Wire - Gestionnaire de Telechargement !");
        }
        loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("/org.openjfx/sample.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.requestFocus();
        primaryStage.show();
    }

  
    public static void main(String[] args) {
        launch(args);
    }

}
