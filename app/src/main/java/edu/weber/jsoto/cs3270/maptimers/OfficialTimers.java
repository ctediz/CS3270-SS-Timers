package edu.weber.jsoto.cs3270.maptimers;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class OfficialTimers extends Fragment {
    private View fragView;
    private static final int NUMBER_OF_CHANNELS = 20;
    private ArrayList<MapObject> channels = new ArrayList<MapObject>(NUMBER_OF_CHANNELS);


    public OfficialTimers() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragView =  inflater.inflate(R.layout.fragment_official_timers, container, false);
        createChannels();

        return fragView;
    }

    // creates MapObjects and assigns them into channels array
    private void createChannels()
    {
        Log.d("OfficialTimers", "createChannels()");
        for(int i = 0; i < NUMBER_OF_CHANNELS; i++)
        {
            channels.add(new MapObject(i));
        }

        /*
        for(int i = 0; i < NUMBER_OF_CHANNELS; i++)
        {
            System.out.println("Channel " + i  + ", " + channels.get(i).getChannel());
            System.out.println("Time = " + channels.get(i).getDate());
        }
        */
    }

}
