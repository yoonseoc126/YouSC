package com.example.yousc;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import org.junit.Test;

public class OutdatedEventMarkerTest {
    @Test
    public void testOutdatedEvents(){
        EventHelper test = new EventHelper();

        Event outdatedEvent = new Event("event1", "location", "10/12/1991", "01:00", "this is details", 0, 0, "test@usc.edu");
        Event validEvent = new Event("event2", "location", "10/12/2050", "01:00", "this is details", 0, 0, "test@usc.edu");

        assertTrue(test.isInFuture(validEvent));
        assertFalse(test.isInFuture(outdatedEvent));
    }
}
