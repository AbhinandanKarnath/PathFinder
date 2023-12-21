package com.example.pathfinder;

public class BlockInfo {

    boolean path , block , destination , source , acq,vis;
    int row;
    int col;

    public BlockInfo(int row , int col)
    {
        this.row = row;
        this.col = col;
        path = true;
        block = false;
        destination = false;
        source = false;
        acq = false;
        vis = false;
    }
    public int getCol()
    {
        return  this.col;
    }

    public int getRow()
    {
        return  this.row;
    }

    public void setCellBlock()
    {
        path = false;
        block = true;
    }

    public void setSource()
    {
        source = true;
        path = false;
        block = false;
        destination=false;
    }

    public void setDestination()
    {
        destination = true;
        path = false;
        source = false;
        block = false;
    }
    public void setAcq(){this.acq = true;}
    public boolean getAcq(){return this.acq;}

    public void setVis()
    {
        this.vis = true;
    }

    public boolean getVis()
    {
        return this.vis;
    }

    public void setPath()
    {

    }
}
