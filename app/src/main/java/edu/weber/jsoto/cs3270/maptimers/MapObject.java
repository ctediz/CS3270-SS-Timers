package edu.weber.jsoto.cs3270.maptimers;

import java.util.Calendar;
import java.util.Date;

public class MapObject {
    private int channel;
    private Date time;
    private String state;   // may be unnecessary

    public MapObject(int channel)
    {
        this.channel = channel;
        updateTime();
    }

    // updates time
    public void updateTime()
    {
        Calendar c = Calendar.getInstance();
        time = c.getTime();
    }

    public Date getDate()
    {
        return time;
    }

    public void setState(String newState)
    {
        state = newState;
    }

    public String getState()
    {
        return state;
    }

    public int getChannel()
    {
        return channel;
    }
}
