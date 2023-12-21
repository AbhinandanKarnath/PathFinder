package com.example.pathfinder;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

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

    Point2D p1;
    Point2D p2;

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
                p1 = new Point2D(sourceX,sourceY);
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
                p2 = new Point2D(destinX,destinY);
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
        System.out.println("Co - ordinates");
        System.out.println(sourceX+" "+sourceY+" "+p1.getX()+" "+ p1.getY());
        System.out.println(destinX+" "+destinY+" "+p2.getX()+" "+p2.getY());
        sp[sourceX][sourceY].setStyle("-fx-background-color: white");

        Task<Void> fin = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Queue<Point2D> queue = new LinkedList<>();
                queue.add(new Point2D(sourceX, sourceY));

                while (!queue.isEmpty()) {
                    Point2D p = queue.remove();
                    int pr2 = (int) p.getX();
                    int pc2 = (int) p.getY();

                    cellInfo[pr2][pc2].setVis();
                    Platform.runLater(() -> {
                        sp[pr2][pc2].setStyle("-fx-background-color: blue");
                        System.out.println(pr2 + " " + pc2);
                    });

                    if (p.equals(p2)) {
                        break;
                    }

                    if (pr2 - 1 > 0 && !cellInfo[pr2 - 1][pc2].getAcq() && !cellInfo[pr2 - 1][pc2].getVis()) {
                        queue.add(new Point2D(pr2 - 1, pc2));
                        cellInfo[pr2 - 1][pc2].setAcq();
                    }
                    if (pc2 + 1 < col && !cellInfo[pr2][pc2 + 1].getAcq() && !cellInfo[pr2][pc2 + 1].getVis()) {
                        queue.add(new Point2D(pr2, pc2 + 1));
                        cellInfo[pr2][pc2 + 1].setAcq();
                    }
                    if (pr2 + 1 < row && !cellInfo[pr2 + 1][pc2].getAcq() && !cellInfo[pr2 + 1][pc2].getVis()) {
                        queue.add(new Point2D(pr2 + 1, pc2));
                        cellInfo[pr2 + 1][pc2].setAcq();
                    }
                    if (pc2 - 1 >= 0 && !cellInfo[pr2][pc2 - 1].getAcq() && !cellInfo[pr2][pc2 - 1].getVis()) {
                        queue.add(new Point2D(pr2, pc2 - 1));
                        cellInfo[pr2][pc2 - 1].setAcq();
                    }

                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                return null;
            }
        };
        new Thread(fin).start();

    }
}

/*
*
                *
                sp[sourceX][sourceY-1].setStyle("-fx-background-color: white");
                sp[sourceX+1][sourceY].setStyle("-fx-background-color: white");
                sp[sourceX][sourceY+1].setStyle("-fx-background-color: white");
                sp[sourceX-1][sourceY].setStyle("-fx-background-color: white");
* */