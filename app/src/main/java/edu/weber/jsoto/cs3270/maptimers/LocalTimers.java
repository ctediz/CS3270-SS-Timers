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
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocalTimers extends GeneralTimer {
    private View fragView;
    private ListView first, second, third;
    //private static final int NUMBER_OF_CHANNELS = 20;
    //private ArrayList<MapObject> channels = new ArrayList<MapObject>(NUMBER_OF_CHANNELS);


    public LocalTimers() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //super.onCreateView(inflater, container, savedInstanceState);
        fragView = super.onCreateView(inflater, container, savedInstanceState);

        // assign list names
        addListListeners(R.array.local_map_change_array);
        nameLists();

        return fragView;
    }

    private void nameLists() {
        TextView txtFirst = (TextView) fragView.findViewById(R.id.txtFirst);
        TextView txtSecond = (TextView) fragView.findViewById(R.id.txtSecond);
        TextView txtThird = (TextView) fragView.findViewById(R.id.txtThird);

        txtFirst.setText(R.string.txtDark);
        txtSecond.setText(R.string.txtGrim);
        txtThird.setText(R.string.txtSpawn);
    }
}
