package com.example.pathfinder;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.util.LinkedList;
import java.util.Queue;

public class MainController {

    @FXML
    Label cellCount ;

    @FXML
    TextField  aniSpeed;
    @FXML
    AnchorPane grid1;
    @FXML
    ChoiceBox<String> choiceBox;

    int row = 21;
    int col = 21;
    static int count = 0;

    static int animation = 100;
    private StackPane[][] sp;
    private BlockInfo[][] cellInfo;
    private StackPane source;
    private StackPane destin;

    private Queue<Point2D> visitedCell ;

    String sourceCol , destiCol = "white";
    String background = "rgb(20,20,20)";
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
                cell.setValue(0);
                sp[i][j].setOnMouseClicked(mouseEvent -> onClick( temp , cell ));

                AnchorPane.setLeftAnchor(sp[i][j], j*22.0);
                AnchorPane.setTopAnchor(sp[i][j], i*22.0);
                                                                                                                        /*                                                                                                                   
                AnchorPane.setLeftAnchor(sp[i][j], j*23.0);
                AnchorPane.setTopAnchor(sp[i][j], i*20.0);
                                                                                                                        */
                sp[i][j].setStyle("-fx-background-color: "+background);                                                           //#FF8000
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

        try
        {
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
                            cellInfo[x][y].setVis(false);
                            sp[x][y].setStyle("-fx-background-color: "+background);                                                    //#FF8000
                            Thread.sleep(5);
                        }
                        size = 0;
                        count = 0 ;
                        source.setStyle("-fx-background-color: "+background);                                            //#FF8000
                        if(destin != null)
                        {
                            destin.setStyle("-fx-background-color: "+background);
                        }
                    }
                    source = cell;
                    source.setStyle("-fx-background-color: "+sourceCol);                                                 //#95190C
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
                            cellInfo[x][y].setVis(false);
                            sp[x][y].setStyle("-fx-background-color: "+background);                                      //#FF8000
                            Thread.sleep(5);
                        }
                        size = 0;
                        count = 0 ;
                        destin.setStyle("-fx-background-color: "+background);                                            //#FF8000
                        if(source != null)
                        {
                            source.setStyle("-fx-background-color: "+background);
                        }
                    }
                    destin = cell;
                    cell.setStyle("-fx-background-color: "+destiCol);                                                    //#0080FF
                    break;
                case "Block":
                    cellIn.setCellBlock();
                    cellIn.setValue(-1);
                    cell.setStyle("-fx-background-color: #0080FF");
                    break;
                case "un-block":
                    cellIn.setPath();
                    cellIn.setValue(0);
                    cell.setStyle("-fx-background-color: "+background);
                    break;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void setAniSpeed()
    {
        try
        {
            if(aniSpeed.equals(""))
            {
                animation = 100;
            }
            else
            {
                animation = Integer.parseInt(aniSpeed.getText());
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            animation = 100;
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
                    count++;
                    int pr2 = (int) p.getX();
                    int pc2 = (int) p.getY();

//                    cellInfo[pr2][pc2].setVis();
                    Platform.runLater(() -> {
                        sp[pr2][pc2].setStyle("-fx-background-color: #FFBF00");

                        cellCount.setText(""+count);
                        System.out.println(pr2 + " " + pc2);
                    });
                    visitedCell.add(p);

                    if (p.equals(p2)) {                                                             //if the source reaches destination
                        Queue<Point2D> path = new Path().findPath(cellInfo , p1 ,p2);
                        Queue<Point2D> temp = new LinkedList<>();

                        try {
                            while(!path.isEmpty())                                                      //create the path
                            {
                                Point2D cell = path.remove();
                                sp[(int)cell.getX()][(int)cell.getY()].setStyle("-fx-background-color: white");
                                temp.add(cell);
                                Thread.sleep(100);
                            }
                        }
                        catch (Exception e)
                        {
                            System.out.println(e.getMessage());
                            System.out.println(e.getCause());
                            break;
                        }

                        while(!queue.isEmpty()) {
                            Point2D reached = queue.remove();
                            int tempX = (int)reached.getX();
                            int tempY = (int)reached.getY();
                            cellInfo[tempX][tempY].setPath();
                            cellInfo[tempX][tempY].setValue(0);
                        }
                        int x , y;

                        while(!visitedCell.isEmpty())
                        {

                            Point2D del = visitedCell.remove();

                            x = (int) del.getX();
                            y = (int) del.getY();
                            cellInfo[x][y].setPath();
                            System.out.println(x+" "+" "+y+" "+cellInfo[x][y].getVis());
                            if(!cellInfo[x][y].getVis())
                            {
                                sp[x][y].setStyle("-fx-background-color: "+background);                                 //#FF8000
                                Thread.sleep(5);
                            }
                        }
                        System.out.println(temp.size());
                        visitedCell = temp;

                        while(!path.isEmpty())
                        {
                            path.remove();
                        }
                        break;
                    }

                    if (pr2 - 1 > -1 && !cellInfo[pr2 - 1][pc2].getAcq()) {
                        queue.add(new Point2D(pr2 - 1, pc2));
                        cellInfo[pr2 - 1][pc2].setAcq();
                        cellInfo[pr2 - 1][pc2].setValue((cellInfo[pr2][pc2].getValue()+1));
                    }
                    if (pc2 + 1 < col && !cellInfo[pr2][pc2 + 1].getAcq()) {
                        queue.add(new Point2D(pr2, pc2 + 1));
                        cellInfo[pr2][pc2 + 1].setAcq();
                        cellInfo[pr2][pc2 + 1].setValue((cellInfo[pr2][pc2].getValue()+1));
                    }
                    if (pr2 + 1 < row && !cellInfo[pr2 + 1][pc2].getAcq()) {
                        queue.add(new Point2D(pr2 + 1, pc2));
                        cellInfo[pr2 + 1][pc2].setAcq();
                        cellInfo[pr2 + 1][pc2].setValue((cellInfo[pr2][pc2].getValue()+1));
                    }
                    if (pc2 - 1 > -1 && !cellInfo[pr2][pc2 - 1].getAcq()) {
                        queue.add(new Point2D(pr2, pc2 - 1));
                        cellInfo[pr2][pc2 - 1].setAcq();
                        cellInfo[pr2][pc2 - 1].setValue((cellInfo[pr2][pc2].getValue()+1));
                    }

                    try {
                        Thread.sleep(animation);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(p2.getX()+" <-> "+p2.getY());
                Thread.sleep(15);
                sp[((int)p2.getX())][((int)p2.getY())].setStyle("-fx-background-color: green");
                Thread.sleep(15);
                sp[((int)p1.getX())][((int)p1.getY())].setStyle("-fx-background-color: #95190C");

                System.out.println("Searched completed................!!!");
                return null;
            }
        };
        new Thread(fin).start();
    }
}