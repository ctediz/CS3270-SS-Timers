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
        fragView =  inflater.inflate(R.layout.fragment_general_timer, container, false);
        Log.d("GeneralTimer", "GeneralTimer onCreateView");

        createChannels();
        addListViews();
        //addListListeners();
        linkChannels();

        return fragView;
    }

    public View getFragView()
    {
        return fragView;
    }

    // creates MapObjects and assigns them into channels array
    protected void createChannels()
    {
        Log.d("GeneralTimer", "createChannels()");
        for(int i = 0; i < NUMBER_OF_CHANNELS; i++)
        {
            channels.add(new MapObject(i+1));   // start with 1 based numbering
        }
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

    public void listSwap(int position, int list, int toWhich)
    {
        ListView toList = (ListView) fragView.findViewById(R.id.lsvThird);   // default
        switch(list)
        {
            case R.id.lsvFirst:
                toList = (ListView) fragView.findViewById(R.id.lsvFirst);
                break;
            case R.id.lsvSecond:
                toList = (ListView) fragView.findViewById(R.id.lsvSecond);
                break;
            case R.id.lsvThird:
                toList = (ListView) fragView.findViewById(R.id.lsvThird);   // default
                break;
            default:
                Log.d("Error", "GeneralTimer.listSwap, could not determine listId");
                break;
        }

        switch(toWhich)
        {
            case 0:
                //R.id.lsvFirst
                swap(position, toList, first);
                Log.d("GeneralTimer", "Moving to first List");
                break;
            case 1:
                //R.id.lsvSecond
                Log.d("GeneralTimer", "Moving to second List");
                break;
            case 2:
                //R.id.lsvThird
                Log.d("GeneralTimer", "Moving to third List");
                break;
            default:
                Log.d("Error", "GeneralTimer.listSwap, could not determine which list to move to");
                break;
        }
    }

    private void swap(int fromPosition, ListView fromList, ListView toList)
    {

    }

    protected void addListListeners(final int resource)
    {
        AdapterView.OnItemClickListener listListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("GeneralTimer", "ListId = " + parent.getId());
                Log.d("GeneralTimer", "Position = " + position + "\nID = " + id);


                MoveMapDialog mmd = new MoveMapDialog();

                // pass arguments to dialog
                Bundle args = new Bundle();
                args.putInt("listPosition", position);
                args.putInt("listID", parent.getId());
                args.putInt("arrayResource", resource);
                mmd.setArguments(args);

                // bring up dialog
                mmd.show(getFragmentManager(), "Dialog");
            }};

        first.setOnItemClickListener(listListener);
        second.setOnItemClickListener(listListener);
        third.setOnItemClickListener(listListener);
    }


}
