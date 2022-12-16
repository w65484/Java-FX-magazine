package com.example.projekt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    /**
     * Entry point of JavaFx application.
     * Loads first scene, sets styles, title and shows the stage.
     * @param stage
     * @throws IOException if the fxml file is not found
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1080, 720 );
        scene.getStylesheets().add("style.css");
        stage.setTitle("Baza danych produktów");
        stage.setScene(scene);
        stage.show();
    }
}