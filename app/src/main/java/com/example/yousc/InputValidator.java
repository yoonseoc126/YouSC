package com.example.yousc;

import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InputValidator {
    public String checkEmptyLogin(String email, String password){
        if(email == null || email.isEmpty())
        {
            return "Please enter your email";
        }else if(password == null || password.isEmpty())
        {
            return "Please enter your password";
        }else
        {
            return null;
        }
    }

    public String checkEmptyAddEvent(String event, String location, String time, String details){
        if (event == null || event.isEmpty() || location == null || location.isEmpty() || time == null || time.isEmpty() || details == null || details.isEmpty()) {
            return "Please fill out all required forms";
        }
        return null;
    }

    public String checkDateFormat(String date){
        if (date.charAt(2) != '/' || date.charAt(5) != '/' || date.length() != 10) {
            return "Date must be formatted in mm/dd/yyyy";
        }
        return null;
    }

    public String checkTimeFormat(String time){
        if (time.charAt(2) != ':' ||time.length() != 5) {
            return "Time must be formatted in hh:mm";
        }
        return null;
    }

    public String checkDateInFuture(String date, String time){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date currentDate = new Date();
        String eventDateTime = date + " " + time;
        try {
            Date eventDate = dateFormat.parse(eventDateTime);
            if (eventDate.after(currentDate)) {
                return null;
            }
        } catch (ParseException e) {
            // error for invalid formats
            Log.e("error parsing event", "error parsing date for event");
            return "Error parsing date for event,";
        }
        return "Event is not in the future.";
    }
}
