package edu.weber.jsoto.cs3270.maptimers;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MapObject {
    private int channel;
    private Date time;
    private int state;
    protected static final int FIRST_STATE = 0;
    protected static final int SECOND_STATE = 1;
    protected static final int THIRD_STATE = 2;

    public MapObject(int channel)
    {
        this.channel = channel;
        updateTime();
        state = THIRD_STATE;
    }

    // updates time
    public void updateTime()
    {
        Calendar c = Calendar.getInstance();
        time = c.getTime();
    }

    public void setTime(String stringTime)
    {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm a");

        try {
            time = format.parse(stringTime);
        } catch (ParseException e) {
            Log.d("Errors", e.toString());
        }
    }

    public void setTime(Date time)
    {
        this.time = time;
    }

    public Date getDate()
    {
        return time;
    }

    public int getChannel()
    {
        return channel;
    }

    public int getState() { return state; }

    public void setState(int newState)
    {
        state = newState;
    }
}
