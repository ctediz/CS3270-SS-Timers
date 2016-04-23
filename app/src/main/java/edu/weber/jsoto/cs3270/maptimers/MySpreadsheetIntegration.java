package edu.weber.jsoto.cs3270.maptimers;

import android.util.Log;

import com.google.gdata.client.spreadsheet.*;
import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.util.*;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class MySpreadsheetIntegration {
    MySpreadsheetIntegration()
    {
        try {
            run();
        }
        catch(MalformedURLException me)
        {
            Log.d("MSI", me.toString());
        }
        catch (AuthenticationException ae)
        {
            Log.d("MSI", ae.toString());
        }
        catch(IOException io)
        {
            Log.d("MSI", io.toString());
        }
        catch(ServiceException se)
        {
            Log.d("MSI", se.toString());
        }
    }

    private void run()throws AuthenticationException, MalformedURLException, IOException, ServiceException
    {
        SpreadsheetService service =
                new SpreadsheetService("MySpreadsheetIntegration-v1");
        service.setProtocolVersion(SpreadsheetService.Versions.V3);

        // TODO: Authorize the service object for a specific user (see other sections)

        // Define the URL to request.  This should never change.
        URL SPREADSHEET_FEED_URL = new URL(
                "https://spreadsheets.google.com/feeds/list/1Xg8Ko8DOg2VIf02R6Bsv7stpDQHEJwJcXBen7JkO9Kk/od6/public/full");

        // Make a request to the API and get all spreadsheets.
        SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
        List<SpreadsheetEntry> spreadsheets = feed.getEntries();

        // Iterate through all of the spreadsheets returned
        for (SpreadsheetEntry spreadsheet : spreadsheets) {
            // Print the title of this spreadsheet to the screen
            System.out.println(spreadsheet.getTitle().getPlainText());

        }
        System.out.println("Done");
    }

    public static void main(String[] args)
            throws AuthenticationException, MalformedURLException, IOException, ServiceException {
        SpreadsheetService service =
                new SpreadsheetService("MySpreadsheetIntegration-v1");
        service.setProtocolVersion(SpreadsheetService.Versions.V3);

        // TODO: Authorize the service object for a specific user (see other sections)

        // Define the URL to request.  This should never change.
        URL SPREADSHEET_FEED_URL = new URL(
                "https://spreadsheets.google.com/feeds/list/1Xg8Ko8DOg2VIf02R6Bsv7stpDQHEJwJcXBen7JkO9Kk/od6/public/values");

        // Make a request to the API and get all spreadsheets.
        SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
        List<SpreadsheetEntry> spreadsheets = feed.getEntries();

        // Iterate through all of the spreadsheets returned
        for (SpreadsheetEntry spreadsheet : spreadsheets) {
            // Print the title of this spreadsheet to the screen
            System.out.println(spreadsheet.getTitle().getPlainText());

        }
        System.out.println("Done");
    }
}