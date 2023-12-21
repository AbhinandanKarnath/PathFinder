package com.example.pathfinder;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.util.LinkedList;
import java.util.Queue;

public class MainController {
    @FXML
    AnchorPane grid1;
    @FXML
    ChoiceBox<String> choiceBox;

    int row = 25;
    int col = 25;
    private StackPane[][] sp;
    private BlockInfo[][] cellInfo;
    private StackPane source;
    private StackPane destin;

    private Queue<Point2D> visitedCell ;
    Point2D p1;
    Point2D p2;

    @FXML
    void initialize()
    {
        sp = new StackPane[row][col];
        cellInfo = new BlockInfo[row][col];
        visitedCell = new LinkedList<>();
        choiceBox.getItems().add(0,"Source");
        choiceBox.getItems().add(1,"Destination");
        choiceBox.getItems().add(2,"Block");
        choiceBox.getItems().add(3,"un-block");


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
                sp[i][j].setStyle("-fx-background-color: #FF8000;");
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
    public  void onClick(StackPane cell , BlockInfo cellIn)
    {
        String selected = choiceBox.getValue();

        if(selected == null)
        {
            return;
        }
        int size = visitedCell.size();
        int x , y;
        switch (selected)
        {
            case "Source":
                cellIn.setSource();
                p1 = new Point2D(cellIn.getRow(), cellIn.getCol());
                if(source != null)
                {
                    for(int i = 0 ; i< size ; i++)
                    {
                        Point2D del = visitedCell.remove();
                        x = (int) del.getX();
                        y = (int) del.getY();
                        cellInfo[x][y].setPath();
                        sp[x][y].setStyle("-fx-background-color: #FF8000");
                    }
                    source.setStyle("-fx-background-color: #FF8000");
                }
                source = cell;
                source.setStyle("-fx-background-color: #95190C");
                break;
            case "Destination":
                p2 = new Point2D(cellIn.getRow(), cellIn.getCol());
                cellIn.setDestination();
                if(destin != null)
                {
                    for(int i = 0 ; i< size ; i++)
                    {
                        Point2D del = visitedCell.remove();
                        x = (int) del.getX();
                        y = (int) del.getY();
                        cellInfo[x][y].setPath();
                        sp[x][y].setStyle("-fx-background-color: #FF8000");
                    }
                    destin.setStyle("-fx-background-color: #FF8000");
                }
                destin = cell;
                cell.setStyle("-fx-background-color: #0080FF");
                break;
            case "Block":
                cellIn.setCellBlock();
                cell.setStyle("-fx-background-color: #0F1A20");
                break;
            case "un-block":
                cellIn.setPath();
                cell.setStyle("-fx-background-color: #FF8000");
                break;
        }
    }

    @FXML
    public void findPath()
    {
        Task<Void> fin = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Queue<Point2D> queue = new LinkedList<>();
                queue.add(p1);

                while (!queue.isEmpty()) {
                    Point2D p = queue.remove();
                    int pr2 = (int) p.getX();
                    int pc2 = (int) p.getY();

                    cellInfo[pr2][pc2].setVis();
                    Platform.runLater(() -> {
                        sp[pr2][pc2].setStyle("-fx-background-color: #FFBF00");
                        System.out.println(pr2 + " " + pc2);
                    });
                    visitedCell.add(p);

                    if (p.equals(p2)) {
                        break;
                    }

                    if (pr2 - 1 > -1 && !cellInfo[pr2 - 1][pc2].getAcq()) {
                        queue.add(new Point2D(pr2 - 1, pc2));
                        cellInfo[pr2 - 1][pc2].setAcq();
                    }
                    if (pc2 + 1 < col && !cellInfo[pr2][pc2 + 1].getAcq()) {
                        queue.add(new Point2D(pr2, pc2 + 1));
                        cellInfo[pr2][pc2 + 1].setAcq();
                    }
                    if (pr2 + 1 < row && !cellInfo[pr2 + 1][pc2].getAcq()) {
                        queue.add(new Point2D(pr2 + 1, pc2));
                        cellInfo[pr2 + 1][pc2].setAcq();
                    }
                    if (pc2 - 1 > -1 && !cellInfo[pr2][pc2 - 1].getAcq()) {
                        queue.add(new Point2D(pr2, pc2 - 1));
                        cellInfo[pr2][pc2 - 1].setAcq();
                    }

                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                sp[((int)p1.getX())][((int)p1.getY())].setStyle("-fx-background-color: #95190C");
                sp[((int)p2.getX())][((int)p2.getY())].setStyle("-fx-background-color: green");
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