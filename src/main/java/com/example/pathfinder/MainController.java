package com.example.pathfinder;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class MainController {
    @FXML
    AnchorPane grid1;
    @FXML
    ChoiceBox<String> choiceBox;

    int row = 21;
    int col = 21;
    private StackPane[][] sp;
    private BlockInfo[][] cellInfo;
    private StackPane source;
    private StackPane destin;
    int sourceX = -1;
    int sourceY = -1;
    int destinX = -1;
    int destinY = -1;

    @FXML
    void initialize()
    {
        sp = new StackPane[row][col];
        cellInfo = new BlockInfo[row][col];
        choiceBox.getItems().add(0,"Source");
        choiceBox.getItems().add(1,"Destination");
        choiceBox.getItems().add(2,"Block");

        for(int i = 0 ; i< row ; i++)
        {
            for( int j = 0 ; j < col ;j++)
            {
                sp[i][j] = new StackPane();

                cellInfo[i][j] = new BlockInfo( i , j );
                sp[i][j].setPrefWidth(20);
                sp[i][j].setPrefHeight(20);

//                sp.setOnMouseExited(mouseEvent -> onOver(sp));
                StackPane temp = sp[i][j];
                BlockInfo cell = cellInfo[i][j];
                sp[i][j].setOnMouseClicked(mouseEvent -> onClick( temp , cell ));

                AnchorPane.setLeftAnchor(sp[i][j], j*23.0);
                AnchorPane.setTopAnchor(sp[i][j], i*20.0);
                sp[i][j].setStyle("-fx-background-color: orange;");
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
                sourceX = cellInfo.getRow();
                sourceY = cellInfo.getCol();
                if(source != null)
                {
                    source.setStyle("-fx-background-color: lightgray");
                }
                source = cell;
                source.setStyle("-fx-background-color: red");
                break;
            case "Destination":
                destinX = cellInfo.getRow();
                destinY = cellInfo.getCol();
                cellInfo.setDestination();
                if(destin != null)
                {
                    destin.setStyle("-fx-background-color: lightgray");
                }
                destin = cell;
                cell.setStyle("-fx-background-color: green");
                break;
            case "Block":
                cellInfo.setCellBlock();
                cell.setStyle("-fx-background-color: black");
                break;
        }
    }

    @FXML
    public void findPath()
    {
        Task<Void> fin = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(() -> {
                    for(int i = sourceY ; i< destinY ; i++)
                    {
                        sp[destinX][i].setStyle("-fx-background-color: blue");
                    }

                    for(int i = sourceX ; i< destinX ; i++)
                    {
                        sp[i][sourceY].setStyle("-fx-background-color: blue");
                    }

                });
                return null;
            }
        };
        new Thread(fin).start();
    }
}