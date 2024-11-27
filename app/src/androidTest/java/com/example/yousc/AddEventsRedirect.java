package com.example.yousc;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@RunWith(AndroidJUnit4.class)
public class AddEventsRedirect {

    @Rule
    public ActivityScenarioRule<AddEventActivity> activityRule = new ActivityScenarioRule<>(AddEventActivity.class);

    @Test
    public void testCreateEventRedirect() {
        // Initialize test values for sample event
        String testEventName = "Test Event";
        String testLocation = "3201 S Hoover St, Los Angeles, CA 90007";
        String testDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
        String testTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        String testDetails = "Test Details";


        // Populate with event details
        onView(withId(R.id.editEventName))
                .perform(typeText(testEventName));
        onView(withId(R.id.editLocation))
                .perform(typeText(testLocation));
        onView(withId(R.id.editEventDate))
                .perform(typeText(testDate));
        onView(withId(R.id.editEventTime))
                .perform(typeText(testTime));
        onView(withId(R.id.editDetails))
                .perform(typeText(testDetails));

        // Click the "Create Event" button
        onView(withId(R.id.createEventButton)).perform(click());

        // Short timeout to account for redirection lag
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check if we are redirected to the Map View
        onView(withId(R.id.map)).check(matches(isDisplayed()));
    }
}
