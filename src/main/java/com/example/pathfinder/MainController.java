package com.example.pathfinder;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class MainController {
    @FXML
    AnchorPane grid1;
    @FXML
    ChoiceBox<String> choiceBox;

    private StackPane[][] sp;
    private StackPane source;
    private StackPane destin;

    @FXML
    void initialize()
    {
        sp = new StackPane[18][21];
        choiceBox.getItems().add(0,"Source");
        choiceBox.getItems().add(1,"Destination");
        choiceBox.getItems().add(2,"Block");

        for(int i = 0 ; i< 18 ; i++)
        {
            for( int j = 0 ; j < 21 ;j++)
            {
                sp[i][j] = new StackPane();

                BlockInfo cellInfo = new BlockInfo();
                sp[i][j].setPrefWidth(20);
                sp[i][j].setPrefHeight(20);

//                sp.setOnMouseExited(mouseEvent -> onOver(sp));
                StackPane temp = sp[i][j];
                sp[i][j].setOnMouseClicked(mouseEvent -> onClick( temp , cellInfo));

                AnchorPane.setLeftAnchor(sp[i][j], j*23.0);
                AnchorPane.setTopAnchor(sp[i][j], i*20.0);
                sp[i][j].setStyle("-fx-background-color: lightgray;");
                grid1.getChildren().add(sp[i][j]);
            }
        }

    }

    @FXML
    public void onOver(StackPane sp)
    {
         sp.setStyle("-fx-background-color: black;");
    }

    @FXML
    public  void onClick(StackPane cell , BlockInfo cellInfo)
    {
        String selected = choiceBox.getValue();

        if(selected == null)
        {
            return;
        }
        switch (selected)
        {
            case "Source":
                cellInfo.setSource();
                if(source != null)
                {
                    source.setStyle("-fx-background-color: lightgray");
                }
                source = cell;
                source.setStyle("-fx-background-color: red");
                break;
            case "Destination":
                cellInfo.setDestination();
                if(destin != null)
                {
                    destin.setStyle("-fx-background-color: lightgray");
                }
                destin = cell;
                cell.setStyle("-fx-background-color: green");
                break;
            case "Block":
                cellInfo.setCellBaricade();
                cell.setStyle("-fx-background-color: black");
                break;
        }
    }

    @FXML
    public void findPath()
    {

    }
}