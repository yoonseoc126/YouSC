//package com.example.yousc;
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//
//import static org.junit.Assert.assertEquals;
//
//import android.content.Intent;
//
//import androidx.test.core.app.ActivityScenario;
//import androidx.test.ext.junit.rules.ActivityScenarioRule;
//
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;
//
//import org.junit.Rule;
//import org.junit.Test;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//
//public class RenderEventDetails {
//    @Rule
//    public ActivityScenarioRule<MapsActivity> activityRule =
//            new ActivityScenarioRule<>(MapsActivity.class);
//
//    @Test
//    public void testEventDetailsPage() {
//        ActivityScenario<MapsActivity> scenario = ActivityScenario.launch(MapsActivity.class);
//        String testEventName = "Test Event";
//        String testLocation = "3201 S Hoover St, Los Angeles, CA 90007";
//        String testDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
//        String testTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
//        String testDetails = "Test Details";
//        String testEmail = "test@gmail.com";
//
//        scenario.onActivity(activity -> {
//            GoogleMap mMap = activity.mMap;
//
//            // Mock event and add marker
//            Event mockEvent = new Event(testEventName, testLocation, testDate, testTime, testDetails, 0, 0, testEmail);
//            LatLng testLatLng = new LatLng(34.0219, -118.2856);
//            MarkerOptions markerOptions = new MarkerOptions()
//                    .position(testLatLng)
//                    .title(mockEvent.getName());
//            Marker marker = mMap.addMarker(markerOptions);
//
//            // Map the marker to the event in the activity's HashMap
//            activity.eventToPinMap.put(marker.getId(), mockEvent);
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            // Simulate marker click
//            mMap.setOnMarkerClickListener(clickedMarker -> {
//                try {
//                    Thread.sleep(10000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                assertEquals("Mock Event", clickedMarker.getTitle());
//                return true;
//            });
////            mMap.getOnMarkerClickListener().onMarkerClick(marker);
//        });
//    }
//}

package com.example.yousc;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import android.view.View;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class RenderEventDetails {
    @Rule
    public ActivityScenarioRule<MapsActivity> activityRule = new ActivityScenarioRule<>(MapsActivity.class);

    @Test
    public void testEventDetailsPage() {
        ActivityScenario<MapsActivity> scenario = ActivityScenario.launch(MapsActivity.class);

        // Test event data
        String testEventName = "Test Event";
        String testLocation = "3201 S Hoover St, Los Angeles, CA 90007";
        String testDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
        String testTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        String testDetails = "Test Details";
        String testEmail = "test@gmail.com";

        final Marker[] testMarker = new Marker[1];

        scenario.onActivity(activity -> {
            GoogleMap mMap = activity.mMap;

            // Set marker on fake event on campus
            Event mockEvent = new Event(testEventName, testLocation, testDate, testTime, testDetails, 0, 0, testEmail);
            LatLng testLatLng = new LatLng(34.0219, -118.2856);
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(testLatLng)
                    .title(mockEvent.getName());
            testMarker[0] = mMap.addMarker(markerOptions);

            activity.eventToPinMap.put(testMarker[0].getId(), mockEvent);

            mMap.setOnMarkerClickListener(marker -> {
                if (marker.equals(testMarker[0])) {
                    return true;
                }
                else {
                    return false;
                }
            });
           // need to use our clicking listener function here so does this constitute as a non-black box?
        });

        onView(withId(R.layout.event_description_window))
                .check(matches(isDisplayed()));

        // Verify event details are correct
        onView(withId(R.id.eventName))
                .check(matches(withText(testEventName)));
        onView(withId(R.id.location))
                .check(matches(withText(testLocation)));
        onView(withId(R.id.date))
                .check(matches(withText(testDate)));
        onView(withId(R.id.eventTime))
                .check(matches(withText(testTime)));
        onView(withId(R.id.editDetails))
                .check(matches(withText(testDetails)));
    }
}