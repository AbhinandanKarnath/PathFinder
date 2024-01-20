package com.example.pathfinder;

import javafx.geometry.Point2D;

import java.util.LinkedList;
import java.util.Queue;

public class Path {

    public Path(){
    }

    public Queue<Point2D> findPath(BlockInfo[][] cellInfo , Point2D sou , Point2D des)
    {

        Queue<Point2D> path = new LinkedList<>();

        try
        {
            int sx = (int) sou.getX();
            int sy = (int) sou.getY();
            int dx = (int) des.getX();
            int dy = (int) des.getY();
            int val = cellInfo[dx][dy].getValue() - 1 ;

            while (sx != dx || sy != dy)
            {
                if(val == cellInfo[dx-1][dy].getValue())
                {
                    dx = dx -1;
                }
                else
                if(val == cellInfo[dx][dy-1].getValue())
                {
                    dy = dy-1;
                }
                else
                if(val == cellInfo[dx+1][dy].getValue())
                {
                    dx = dx+1;
                }
                else
                if(val == cellInfo[dx][dy+1].getValue()) {
                    dy = dy + 1;
                }
                else
                {
                    System.out.println(dx+" "+dy+" "+val);
                    break;
                }
                System.out.println(dx +" "+dy);
                path.add(new Point2D(dx,dy));
                cellInfo[dx][dy].setVis(true);
                val = val-1;
            }
            System.out.println("sending back the array......");
            return path;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            return path;
        }
    }

    public Integer xAxisPath()
    {
        return  xAxisPath();
    }

    public Integer yAxisPath()
    {
        return yAxisPath();
    }
}