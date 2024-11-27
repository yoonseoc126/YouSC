package com.example.yousc;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.LatLng;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

@RunWith(MockitoJUnitRunner.Silent.class)
public class OnMarkerClickTest {

    private MapsActivity mapsActivity;
    private Event testEvent;

    @Before
    public void setUp() {
        mapsActivity = new MapsActivity();
        mapsActivity.eventToPinMap = new HashMap<>();
        String testEventName = "Test Event";
        String testLocation = "3201 S Hoover St, Los Angeles, CA 90007";
        String testDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
        String testTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        String testDetails = "Test Details";
        String testEmail = "testEmail@usc.edu";
        testEvent = new Event(testEventName, testLocation, testDate, testTime, testDetails, 0, 0, testEmail);
    }

    @Test
    public void testOnMarkerClickSuccess() {
        String markerId = "test_marker_id";
        mapsActivity.eventToPinMap.put(markerId, testEvent);

//        Marker marker = new MarkerBuilder()
//                .setId(markerId)
//                .setPosition(new LatLng(34.0206, -118.2854))
//                .build();

//        boolean result = mapsActivity.onMarkerClick(marker);

        // Assert
//        assertTrue("Marker click should return true", result);
        // Add additional assertions based on your implementation
    }

    // Create out own marker class because private API error if we try to access Google's
    private static class MarkerBuilder {
        private String id;
        private LatLng position;

        public Marker build() {
//            return new Marker() {
//                @Override
//                public String getId() {
//                    return id;
//                }
//
//                @Override
//                public LatLng getPosition() {
//                    return position;
//                }
//            };
            return null;
        }
    }
}