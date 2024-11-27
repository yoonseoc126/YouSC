package com.example.yousc;

import android.util.Log;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventHelper {
    // Given date in MM/dd/yyyy format, returns true if date is in future of current data
    public boolean isInFuture(Event event){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date currentDate = new Date();
        String eventDateTime = event.getDate() + " " + event.getTime();
        System.out.println("Printing date: " + eventDateTime);
        try {
            Date eventDate = dateFormat.parse(eventDateTime);
            if (eventDate.after(currentDate)) {
                return true;
            }
        } catch (ParseException e) {
            // error for invalid formats
            Log.e("error parsing event", "error parsing date for event");
            return false;
        }
        return false;
    }

    public String isWithinFryft(double latitude, double longitude){
        if(latitude < 34.010860 || latitude > 34.031064 || longitude < -118.300248 || longitude > -118.264672){
            return "Event must be within USC Fryft zone";
        }
        return null;
    }
}
