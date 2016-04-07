package edu.weber.jsoto.cs3270.maptimers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocalTimers extends GeneralTimer {
    private View fragView;
    private ListView first, second, third;
    private static final int NUMBER_OF_CHANNELS = 20;
    private ArrayList<MapObject> channels = new ArrayList<MapObject>(NUMBER_OF_CHANNELS);


    public LocalTimers() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragView =  inflater.inflate(R.layout.fragment_local_timers, container, false);
        Log.d("LocalTimers", "LocalTimers onCreateView");

        createChannels();
        addListViews();
        addListListeners();
        linkChannels();

        return fragView;
    }
}
