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
    public ActivityScenarioRule<MapsActivity> activityRule =
            new ActivityScenarioRule<>(MapsActivity.class);

    // Helper method to wait for map to be ready
    private void waitForMapReady(GoogleMap map, long timeout) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        map.setOnMapLoadedCallback(latch::countDown);
        latch.await(timeout, TimeUnit.SECONDS);
    }

    @Test
    public void testEventDetailsPage() throws InterruptedException {
        ActivityScenario<MapsActivity> scenario = ActivityScenario.launch(MapsActivity.class);

        // Test event data
        String testEventName = "Test Event";
        String testLocation = "3201 S Hoover St, Los Angeles, CA 90007";
        String testDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
        String testTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        String testDetails = "Test Details";
        String testEmail = "test@gmail.com";

        // Reference to hold the marker
        final Marker[] testMarker = new Marker[1];
        final CountDownLatch markerClickLatch = new CountDownLatch(1);

        scenario.onActivity(activity -> {
            GoogleMap mMap = activity.mMap;

            // Create mock event and marker
            Event mockEvent = new Event(testEventName, testLocation, testDate, testTime, testDetails, 0, 0, testEmail);
            LatLng testLatLng = new LatLng(34.0219, -118.2856);
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(testLatLng)
                    .title(mockEvent.getName());
            testMarker[0] = mMap.addMarker(markerOptions);

            // Map the marker to the event
            activity.eventToPinMap.put(testMarker[0].getId(), mockEvent);

            // Set up marker click listener
            mMap.setOnMarkerClickListener(marker -> {
                if (marker.equals(testMarker[0])) {
                    markerClickLatch.countDown();
                    return true;
                }
                return false;
            });

            try {
                // Wait for map to be ready
                waitForMapReady(mMap, 10);

                // Simulate marker click on UI thread
                activity.runOnUiThread(() -> {
                    // Trigger the marker click callback directly
                    testMarker[0].showInfoWindow();
                    // You might need to call your custom method that handles marker clicks
                    // For example, if you have a method like handleMarkerClick(Marker marker):
                    // activity.handleMarkerClick(testMarker[0]);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Wait for marker click to be processed
        markerClickLatch.await(5, TimeUnit.SECONDS);

        // Wait for UI to update
        Thread.sleep(1000);

        // Verify the event description window is displayed
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

//        // Test closing the description window
//        onView(withId(R.id.closeDescButton))
//                .perform(click());
//        onView(withId(R.layout.event_description_window))
//                .check(matches(not(isDisplayed())));
    }

    // Alternative test focusing just on marker click behavior
//    @Test
//    public void testMarkerClick() throws InterruptedException {
//        ActivityScenario<MapsActivity> scenario = ActivityScenario.launch(MapsActivity.class);
//        final CountDownLatch markerClickLatch = new CountDownLatch(1);
//
//        scenario.onActivity(activity -> {
//            GoogleMap mMap = activity.mMap;
//
//            // Create test marker
//            LatLng testLatLng = new LatLng(34.0219, -118.2856);
//            MarkerOptions markerOptions = new MarkerOptions()
//                    .position(testLatLng)
//                    .title("Test Marker");
//            Marker marker = mMap.addMarker(markerOptions);
//
//            // Set up marker click listener
//            mMap.setOnMarkerClickListener(clickedMarker -> {
//                if (clickedMarker.equals(marker)) {
//                    markerClickLatch.countDown();
//                    return true;
//                }
//                return false;
//            });
//
//            // Simulate click
//            activity.runOnUiThread(() -> {
//                marker.showInfoWindow();
//                // If you have a custom method to handle marker clicks, call it here
//                // activity.handleMarkerClick(marker);
//            });
//        });
//
//        // Verify that marker click was processed
//        assert markerClickLatch.await(5, TimeUnit.SECONDS);
//    }
}