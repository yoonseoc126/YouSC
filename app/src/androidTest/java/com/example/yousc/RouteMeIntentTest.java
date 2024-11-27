package com.example.yousc;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.action.MotionEvents;

import com.example.yousc.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.Projection;

import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.view.View;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

public class RouteMeIntentTest {

    @Rule
    public ActivityScenarioRule<MapsActivity> activityRule =
            new ActivityScenarioRule<>(MapsActivity.class);

    @Test
    public void testRouteMeButton() {
        // Initialize Intents for capturing the navigation intent
        Intents.init();

        // Mock event and marker setup
        LatLng testLatLng = new LatLng(34.0206, -118.2854); // Example coordinates
        String testEventName = "Test Event";

        // Add a test marker and simulate clicking it
        activityRule.getScenario().onActivity(activity -> {
            SupportMapFragment mapFragment = (SupportMapFragment) activity.getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            if (mapFragment != null) {
                mapFragment.getMapAsync(googleMap -> {
                    Marker testMarker = googleMap.addMarker(new MarkerOptions()
                            .position(testLatLng)
                            .title(testEventName));
                    if (testMarker != null) {
                        testMarker.setTag("test_event_id");
                        googleMap.setOnMarkerClickListener(marker -> {
                            activity.onMarkerClick(marker); // Trigger your onMarkerClick logic
                            return true;
                        });

                        googleMap.getProjection().toScreenLocation(testLatLng); // Ensure it maps to screen
                    }
                });
            }
        });

        onView(withId(R.id.closeDescButton))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        onView(withId(R.id.routeMeButton))
                .perform(ViewActions.click());

        Intents.intended(allOf(
                hasAction(Intent.ACTION_VIEW),
                hasData(Uri.parse("http://maps.google.com/maps?daddr=34.0206,-118.2854"))
        ));

        Intents.release();
    }
}
