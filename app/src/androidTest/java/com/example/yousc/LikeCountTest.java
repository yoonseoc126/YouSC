package com.example.yousc;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Intent;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class InitialVotesCountTest {

    public ActivityScenarioRule<voteActivity> activityRule =
            new ActivityScenarioRule<>(voteActivity.class);


    @Test
    public void validateInitialVoteCounts() {
//        Event testEvent = new Event("KT home", "1207 w 27th St, Los Angeles, CA 90007", "11/25/2026", "12:00",
//                "blah blah blah", 0, 0, "author@example.com");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        String eventId = "-OBb3goIu5V2SJLW1zGd";

        Event testEvent = new Event(
                "fun event",  // Name
                "610 Childs Way, Los Angeles, CA 90089",  // Location
                "12/12/2024",  // Date
                "12:23",  // Time
                "asdfasdfas",  // Details
                5,  // Upvotes
                1,  // Downvotes
                "jbcha@usc.edu"  // Author Email
        );

        databaseReference.child("events").child(eventId).setValue(testEvent);


        // Simulate displaying these initial counts
        onView(withId(R.id.upvoteCount)).check(matches(withText("5")));
        onView(withId(R.id.downvoteCount)).check(matches(withText("1")));


    }


}