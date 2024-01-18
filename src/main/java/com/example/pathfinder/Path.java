package com.example.pathfinder;

import javafx.geometry.Point2D;

import java.util.LinkedList;
import java.util.Queue;

public class Path {

    public Path(){
    }

    public Queue<Point2D> findPath(BlockInfo[][] cellInfo , Point2D sou , Point2D des)
    {
        int sx = (int) sou.getX();
        int sy = (int) sou.getY();
        int dx = (int) des.getX();
        int dy = (int) des.getY();
        int val = cellInfo[dx][dy].getValue() - 1 ;

        Queue<Point2D> path = new LinkedList<>();

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
            val = val-1;
        }

        return path;
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