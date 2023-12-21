package com.example.pathfinder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),700,500);
        stage.setTitle("The path finder");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        try
        {
            launch();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}