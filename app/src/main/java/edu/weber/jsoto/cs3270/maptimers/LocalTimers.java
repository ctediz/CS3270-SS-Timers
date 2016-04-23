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
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocalTimers extends GeneralTimer {
    private View fragView;
    private ArrayList<MapObject> channelsFirst, channelsSecond, channelsThird;

    public LocalTimers() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragView = super.onCreateView(inflater, container, savedInstanceState);
        createChannels();

        // assign list names
        addListListeners(R.array.local_map_change_array);

        nameLists(R.string.txtDark, R.string.txtGrim, R.string.txtSpawn);

        // Get GeneralTimer channels to manipulate
        getChannels();

        return fragView;
    }

    @Override
    public void onResume() {
        super.onResume();
        clearListViews();
        Log.d("LocalTimer", "onResume");

        GsonBuilder gsonB = new GsonBuilder();
        Gson gson = gsonB.create();
        //MapObject[] mo = null;
        ArrayList<MapObject> mapAL = new ArrayList<MapObject>();
        String holder;
        SharedPreferences prefs = getActivity().getPreferences(FragmentActivity.MODE_PRIVATE);

        Type listType = new TypeToken<ArrayList<MapObject>>() {
        }.getType();

        // first list
        holder = prefs.getString("first", "");
        Log.d("LocalTimer", "Grabbing first list\n\t" + holder);
        if(!holder.equals("")) {
            mapAL = gson.fromJson(holder, listType);
            channelsFirst = mapAL;
        }

        // second list
        holder = prefs.getString("second", "");
        Log.d("LocalTimer", "Grabbing second list\n\t" + holder);
        if(!holder.equals("")){
            mapAL = gson.fromJson(holder, listType);
            channelsSecond = mapAL;
        }

        // third list
        holder = prefs.getString("third", "");
        Log.d("LocalTimer", "Grabbing third list\n\t" + holder);
        if(!holder.equals("")){
            mapAL = gson.fromJson(holder, listType);
            channelsThird = mapAL;
        }

        setChannels(channelsFirst, channelsSecond, channelsThird);
        displayChannels();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("LocalTimer", "onPause");

        // set up local channels for GeneralTimer
        setChannels(channelsFirst, channelsSecond, channelsThird);

        // Create Json for each item in ListView
        storeListView();

        // Clear ListViews
            // Done in onResume
        // clearListViews();

        // trim channels
            // done in storeListView
        // trimChannels();
    }

    private void getChannels()
    {
        channelsFirst = getFirstChannel();
        channelsSecond = getSecondChannel();
        channelsThird = getThirdChannel();
    }
}
