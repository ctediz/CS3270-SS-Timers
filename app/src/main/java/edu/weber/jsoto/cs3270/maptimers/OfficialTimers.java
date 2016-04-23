package edu.weber.jsoto.cs3270.maptimers;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gdata.client.spreadsheet.FeedURLFactory;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class OfficialTimers extends GeneralTimer {
    private View fragView;
    //private static final String KEY = "1mU9G7t0zaQTWLmQyhkDAcBE_QKqwN03WDJxpzeSfxEY";   // My Spreadsheet key
    private static final String KEY = "1w5cJpOd7oZyuT_NjWBfcUCOyxiZ4A2AKUvaJwZgppL8";   // Actual Spreadsheet key (not published)
    private ArrayList<MapObject> firstChannel, secondChannel, thirdChannel;
    private static final int OFFSET = 1;    // TimeZone offset
    private Timer updateTimer;
    private AsyncTask asyncUpdate;

    public OfficialTimers() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragView =  super.onCreateView(inflater, container, savedInstanceState);

        nameLists(R.string.txtHotWarm, R.string.txtGrim, R.string.txtMissed);

        return fragView;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("OfficialTimers", "onPause");
        updateTimer.cancel();
        asyncUpdate.cancel(true);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("OfficialTimers", "onResume");
        //getChannels();
        startTimer();
    }

    public class runAsync extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params) {
            try {
                run();
            } catch (MalformedURLException me) {
                Log.d("Errors", me.toString());
            } catch (AuthenticationException ae) {
                Log.d("Errors", ae.toString());
            } catch (IOException io) {
                Log.d("Errors", io.toString());
            } catch (ServiceException se) {
                Log.d("Errors", se.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            clearListViews();
            setChannels(firstChannel, secondChannel, thirdChannel);
            displayChannels();

            //startTimer();
        }

        private void run() throws AuthenticationException, MalformedURLException, IOException, ServiceException
        {
            SpreadsheetService service =
                    new SpreadsheetService("MySpreadsheetIntegration-v1");
            // Get spreadsheet URL
            URL SPREADSHEET_FEED_URL = new URL ("https://spreadsheets.google.com/feeds/worksheets/" + KEY + "/public/full");

            // Get worksheet
            WorksheetFeed worksheetFeed = service.getFeed(SPREADSHEET_FEED_URL, WorksheetFeed.class);
            List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
            WorksheetEntry worksheet = worksheets.get(0);

            // Get cells
            URL cellFeedUrl = null;
            CellFeed cellFeed = null;
            try {
                // get only range needed
                cellFeedUrl = new URI(worksheet.getCellFeedUrl().toString() + "?min-row=4&max-row=23&min-col=7&max-col=7").toURL();
            }
            catch(URISyntaxException e) {
                Log.d("Errors", e.toString());
            }

            cellFeed = service.getFeed(cellFeedUrl, CellFeed.class);

            // Contains Time of last spawn
                // (0-19) = (ch 1 - ch 20)
            List<CellEntry> cells = cellFeed.getEntries();

            Calendar currCal = Calendar.getInstance();
            Calendar sheetCal = Calendar.getInstance();

            Date timeSheet = null;
            SimpleDateFormat format = new SimpleDateFormat("hh:mm a");

            long hourNow;
            long hourSheet;

            //for(CellEntry cell : cells)
            for(int channel = 0; channel < NUMBER_OF_CHANNELS; channel++)
            {
                try {
                    timeSheet = format.parse(cells.get(channel).getCell().getValue());
                    //formatTime = f1.parse(cell.getCell().getValue());
                } catch (ParseException e) {
                    Log.d("Errors", e.toString());
                }

                sheetCal.setTime(timeSheet);

                sheetCal.set(Calendar.DAY_OF_MONTH, currCal.get(Calendar.DAY_OF_MONTH));
                sheetCal.set(Calendar.MONTH, currCal.get(Calendar.MONTH));
                sheetCal.set(Calendar.YEAR, currCal.get(Calendar.YEAR));

                hourNow = currCal.getTimeInMillis();
                hourSheet = sheetCal.getTimeInMillis();

                //Log.d("OfficialTimers", "Time diff: " + ((hourNow - hourSheet - (OFFSET * 60 * 60 * 1000L)) / (60 * 1000L)));
                // To Minutes - TimeZone offset, channel offset +1
                createChannel((hourNow - hourSheet - (OFFSET * 60 * 60 * 1000L)) / (60 * 1000L), channel + 1, timeSheet);
            }
        }
    }

    private void createChannel(long timeDiff, int channel, Date time)
    {
        MapObject fromSheet = new MapObject(channel);
        fromSheet.setTime(time);

        if(timeDiff >=120 || timeDiff < 0)  //Missed - Third List
        {
            thirdChannel.add(fromSheet);
        }
        else if(timeDiff >= 0 && timeDiff < 85)    // Grim - Second List
        {
            secondChannel.add(fromSheet);
        }
        else if(timeDiff >= 85 && timeDiff < 120)   // Hot/Warm - First List
        {
            firstChannel.add(fromSheet);
        }
    }

    private void getChannels()
    {
        Log.d("OfficialTimers", "getChannels");
        firstChannel = new ArrayList<MapObject>();//= getFirstChannel();
        secondChannel = new ArrayList<MapObject>(); //= getSecondChannel();
        thirdChannel = new ArrayList<MapObject>(); //= getThirdChannel();

        //startTimer();
        asyncUpdate = new runAsync().execute("");
    }

    private void startTimer()
    {
        int minutes = 5;
        //startTimer(minutes * 60000L);
        startTimer(1 * 5000L);  // 5 seconds
    }

    private void startTimer(long counter) {
        Log.d("OfficialTimers", "startTimer");

        updateTimer = new Timer();
        updateTimer.schedule(new updateMaps(), 0, counter);
    }

    private class updateMaps extends TimerTask
    {
        @Override
        public void run()
        {
            getChannels();
            //new runAsync().execute("");
        }
    }
}
