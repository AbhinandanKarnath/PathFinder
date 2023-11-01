package com.example.pathfinder;

public class BlockInfo {

    boolean path , block , destination , source;

    public BlockInfo()
    {
        path = true;
        block = false;
        destination = false;
        source = false;
    }

    public void setCellBaricade()
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

    public void setPath()
    {

    }
}
