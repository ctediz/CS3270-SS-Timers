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


/**
 * A simple {@link Fragment} subclass.
 */
public class GeneralTimer extends Fragment {
    private View fragView;
    private ListView first, second, third;
    private static final int NUMBER_OF_CHANNELS = 20;
    private ArrayList<MapObject> channels = new ArrayList<MapObject>(NUMBER_OF_CHANNELS);


    public GeneralTimer() {
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


    // creates MapObjects and assigns them into channels array
    protected void createChannels()
    {
        Log.d("LocalTimers", "createChannels()");
        for(int i = 0; i < NUMBER_OF_CHANNELS; i++)
        {
            channels.add(new MapObject(i+1));   // start with 1 based numbering
        }

        /*
        for(int i = 0; i < NUMBER_OF_CHANNELS; i++)
        {
            System.out.println("Channel " + i  + ", " + channels.get(i).getChannel());
            System.out.println("Time = " + channels.get(i).getDate());
        }
        */
    }

    protected void addListViews()
    {
        first = (ListView) fragView.findViewById(R.id.lsvFirst);
        second = (ListView) fragView.findViewById(R.id.lsvSecond);
        third = (ListView) fragView.findViewById(R.id.lsvThird);
    }

    // adds channels to ListView
    protected void linkChannels()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        ArrayList<String> strAL = new ArrayList<String>();

        for(int i = 0; i < NUMBER_OF_CHANNELS; i++)
        {
            strAL.add("Channel " + channels.get(i).getChannel()+ "\nTime: " + sdf.format(channels.get(i).getDate()));
        }

        ArrayAdapter<String> strAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strAL);
        third.setAdapter(strAdapter);
    }

    protected void addListListeners()
    {
        AdapterView.OnItemClickListener listListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("LocalTimers", "List = " + view.getId());
                Log.d("LocalTimers", "Position = " + position + "\nID = " + id);

                MoveMapDialog mmd = new MoveMapDialog();

                // pass arguments to dialog
                Bundle args = new Bundle();
                args.putInt("listPosition", position);
                args.putInt("listID", view.getId());
                mmd.setArguments(args);

                // bring up dialog
                mmd.show(getFragmentManager(), "Dialog");
            }};

        first.setOnItemClickListener(listListener);
        second.setOnItemClickListener(listListener);
        third.setOnItemClickListener(listListener);

    }
}
