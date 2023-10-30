package com.example.pathfinder;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.EventListener;

public class MainController {
    @FXML
    AnchorPane grid1;

    @FXML
    void initialize()
    {

        for(int i = 0 ; i< 18 ; i++)
        {
            for( int j = 0 ; j < 21 ;j++)
            {
                StackPane sp = new StackPane();

                sp.setPrefWidth(20);
                sp.setPrefHeight(20);

                sp.setOnMouseEntered(mouseEvent -> sp.setStyle("-fx-background-color: white;"));
//                sp.setOnMouseExited(mouseEvent -> onOver(sp));
                sp.setOnMouseClicked(mouseEvent -> onClick(sp));

                AnchorPane.setLeftAnchor(sp, j*23.0);
                AnchorPane.setTopAnchor(sp, i*20.0);
                sp.setStyle("-fx-background-color: lightgray;");
                grid1.getChildren().add(sp);
            }
        }

    }

    @FXML
    public void onOver(StackPane sp)
    {
         sp.setStyle("-fx-background-color: black;");
    }

    @FXML
    public  void onClick(StackPane cell)
    {
        cell.setStyle("-fx-background-color: red");
    }
}