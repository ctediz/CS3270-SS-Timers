package edu.weber.jsoto.cs3270.maptimers;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class GeneralTimer extends Fragment {
    private View fragView;
    private ListView first, second, third;
    private static final int NUMBER_OF_CHANNELS = 20;
    //private ArrayList<ArrayList> lists = new ArrayList<ArrayList>(NUMBER_OF_STATES);
    private ArrayList<MapObject> channelsFirst = new ArrayList<MapObject>();
    private ArrayList<MapObject> channelsSecond = new ArrayList<MapObject>();
    private ArrayList<MapObject> channelsThird = new ArrayList<MapObject>(NUMBER_OF_CHANNELS);


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
        displayChannels();

        return fragView;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Display ListViews
        //displayChannels();
    }

    @Override
    public void onPause() {
        super.onPause();

        // Clear ListViews
        clearListViews();
    }

    public View getFragView()
    {
        return fragView;
    }

    /**
     * Used to display all channel array lists by calling the private displayChannels()
     */
    public void displayChannels()
    {
        displayChannels(channelsFirst);
        displayChannels(channelsSecond);
        displayChannels(channelsThird);
    }



    /**
     * Used to set up private swap() function.
     * @param position  Position in the fromList ListView to be moved.
     * @param fromList  The ListView ID which contains the MapObject.
     * @param toWhich   The list number to be moved to.
     */
    public void listSwap(int position, int fromList, int toWhich)
    {
        Log.d("GeneralTimer", "listSwap, \nposition = " + position +
                "\nfromList = " + fromList + "\ntoWhich = " + toWhich);

        ArrayList<MapObject> fromArray;
        switch(fromList)
        {
            case R.id.lsvFirst:
                fromArray = channelsFirst;
                break;
            case R.id.lsvSecond:
                fromArray = channelsSecond;
                break;
            case R.id.lsvThird:
                fromArray = channelsThird;
                break;
            default:
                fromArray = null;
                Log.d("Error", "GeneralTimer.listSwap, could not determine listId");
                break;
        }

        switch(toWhich)
        {
            case 0:
                swap(position, fromArray, channelsFirst);
                Log.d("GeneralTimer", "Moving to first List");
                break;
            case 1:
                swap(position, fromArray, channelsSecond);
                Log.d("GeneralTimer", "Moving to second List");
                break;
            case 2:
                swap(position, fromArray, channelsThird);
                Log.d("GeneralTimer", "Moving to third List");
                break;
            default:
                Log.d("Error", "GeneralTimer.listSwap, could not determine which list to move to");
                break;
        }
    }

    /**
     * Adds a listener all the ListViews.
     * @param arrayResource The XML ArrayResource used to display the onClick ListView options.
     */
    protected void addListListeners(final int arrayResource)
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
                args.putInt("arrayResource", arrayResource);
                mmd.setArguments(args);

                // bring up dialog
                mmd.show(getFragmentManager(), "Dialog");
            }};

        first.setOnItemClickListener(listListener);
        second.setOnItemClickListener(listListener);
        third.setOnItemClickListener(listListener);
    }

    /**
     * Swaps a MapObject between ArrayLists.
     * @param fromPosition  Index of the MapObject to be swapped in the fromArray.
     * @param fromArray     ArrayList which contains the MapObject to be swapped.
     * @param toArray       ArrayList which will receive the MapObject.
     */
    private void swap(int fromPosition, ArrayList<MapObject> fromArray, ArrayList<MapObject> toArray)
    {
        if(toArray.equals(null) || fromArray.equals(null))    // fromArray should never be null (never called)
            return;

        MapObject temp = fromArray.get(fromPosition);

        fromArray.remove(fromPosition);
        toArray.add(temp);
        Log.d("GeneralTimer", "swap \ntoArray size = " + toArray.size() + "\nfromArray size = " + fromArray.size());

        displayChannels();
    }

    /**
     * Displays channel array list passed in by creating an ArrayAdapter and attaching to appropriate ListView.
     * @param channel channel ArrayList to be displayed
     */
    private void displayChannels(ArrayList<MapObject> channel)
    {
        Log.d("GeneralTimer", "channel size = " + channel.size());
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        ArrayList<String> strAL = new ArrayList<String>();

        for(int i = 0; i < channel.size(); i++)
        {
            strAL.add("Channel " + channel.get(i).getChannel()+ "\nTime: " + sdf.format(channel.get(i).getDate()));
        }

        ArrayAdapter<String> strAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strAL);

        if(channel.equals(channelsFirst))
            first.setAdapter(strAdapter);
        else if(channel.equals(channelsSecond))
            second.setAdapter(strAdapter);
        else if(channel.equals(channelsThird))
            third.setAdapter(strAdapter);

    }

    /**
     * Wires up ListViews.
     */
    protected void addListViews()
    {
        first = (ListView) fragView.findViewById(R.id.lsvFirst);
        second = (ListView) fragView.findViewById(R.id.lsvSecond);
        third = (ListView) fragView.findViewById(R.id.lsvThird);
    }

    /**
     * Initially creates channels.
     * All channels initially start in the last ListView.
     */
    protected void createChannels()
    {
        Log.d("GeneralTimer", "createChannels()");

        // Place all maps into last
        for(int i = 0; i < NUMBER_OF_CHANNELS; i++)
        {
            channelsThird.add(new MapObject(i+1));   // start with 1 based numbering
        }
    }

    private void clearListViews()
    {
        first.setAdapter(null);
        second.setAdapter(null);
        third.setAdapter(null);
    }
}
