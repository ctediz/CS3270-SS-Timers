package edu.weber.jsoto.cs3270.maptimers;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity", "onCreate");
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //addFragments();
        System.out.println("Spreadsheet stuff");
        //new MySpreadsheetIntegration();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.official_timers) {
            // load official_timers if not loaded
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flMainScreen, new OfficialTimers(), "OT")
                    .commit();
            return true;
        }
        else if(id == R.id.custom_timers)
        {
            // load custom_timers if not loaded
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flMainScreen, new CustomTimers(), "CT")
                    .commit();
            return true;
        }
        else if(id == R.id.local_timers)
        {
            // load local_timers if not loaded
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flMainScreen, new LocalTimers(), "LT")
                    .commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Get current fragment
        Fragment displayed = getSupportFragmentManager().findFragmentById(R.id.flMainScreen);

        // Determine fragment being displayed
        if(displayed == getSupportFragmentManager().findFragmentByTag("LT"))
        {
            Log.d("MainActivity", "onPause LT");
            editor.putString("Displayed", "LT");
        }
        else if(displayed == getSupportFragmentManager().findFragmentByTag("CT"))
        {
            Log.d("MainActivity", "onPause CT");
            editor.putString("Displayed", "CT");
        }
        else if(displayed == getSupportFragmentManager().findFragmentByTag("OT"))
        {
            Log.d("MainActivity", "onPause OT");
            editor.putString("Displayed", "OT");
        }

        // Remove current fragment
        getSupportFragmentManager().beginTransaction()
                .remove(displayed)
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity", "onStart");
        //addFragments();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity", "onResume");
        addFragments();
    }

    /**
     * Calls GeneralTimer's listSwap function.
     * @param position  Position in list of item to be moved.
     * @param list      ID of list which contains item being moved.
     * @param toWhich   Index of ListView to recieve item being moved.
     */
    public void moveMap(int position, int list, int toWhich)
    {
        // Get current fragment
        Fragment displayed = getSupportFragmentManager().findFragmentById(R.id.flMainScreen);
        GeneralTimer gt = null;

        // Determine fragment being displayed
        if(displayed == getSupportFragmentManager().findFragmentByTag("LT"))
        {
            gt = (LocalTimers) getSupportFragmentManager().findFragmentById(R.id.flMainScreen);
        }
        else if(displayed == getSupportFragmentManager().findFragmentByTag("CT"))
        {
            gt = (CustomTimers) getSupportFragmentManager().findFragmentById(R.id.flMainScreen);
        }
        else if(displayed == getSupportFragmentManager().findFragmentByTag("OT"))
        {
            gt = (OfficialTimers) getSupportFragmentManager().findFragmentById(R.id.flMainScreen);
        }

        // get currently active time sheet
        Log.d("MainActivity", "Moving maps");

        // move maps
        gt.listSwap(position, list, toWhich);
    }

    private void addFragments()
    {
        Log.d("MainActivity", "addFragments()");
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        String displayed = prefs.getString("Displayed", "none");

        if(displayed.equals("none") || displayed.equals("LT")) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.flMainScreen, new LocalTimers(), "LT")
                    .commit();
        }
        else if(displayed.equals("OT")) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.flMainScreen, new LocalTimers(), "OT")
                    .commit();
        }
        else if(displayed.equals("CT")) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.flMainScreen, new LocalTimers(), "CT")
                    .commit();
        }
    }
}
