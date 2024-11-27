package com.example.yousc;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class UpvoteCountTest {
    private Event event;

    @Before
    public void setUp() {
        event = new Event();
    }

    @Test
    public void testUpvote() {
        event.upvote();
        assertEquals(Integer.valueOf(1), event.getUpvotes());
        assertEquals(Integer.valueOf(0), event.getDownvotes());
    }
}
