package com.example.yousc;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Test;

public class InitialVoteCountTest {

    /* Tests the count # displayed in Comments (#) of the comments page */
    @Test
    public void testInitialVoteCount() {
        DatabaseReference eventRef = FirebaseDatabase.getInstance()
                .getReference("events")
                .child("testEventId");

        Event testEvent = new Event("tester event", "651 W 35th St, Los Angeles, CA 90089", "12/22/2026", "12:34", "details", 0 ,0, "hi@usc.edu");
        testEvent.setUpvotes(0);

        testEvent.upvote();

        // Assert that the upvote count is incremented
        assertEquals(1, testEvent.getUpvotes());
    }

}