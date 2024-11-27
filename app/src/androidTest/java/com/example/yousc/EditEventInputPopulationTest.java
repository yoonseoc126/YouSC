package com.example.yousc;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EditEventInputPopulationTest {

    @Rule
    public ActivityScenarioRule<EditEventActivity> activityRule =
            new ActivityScenarioRule<>(
                    // Pass intent to replicate clicking edit after clicking on event
                    new Intent(ApplicationProvider.getApplicationContext(), EditEventActivity.class)
                            .putExtra("ID", "event123")
                            .putExtra("NAME", "Sample Event")
                            .putExtra("DATE", "11/27/2024")
                            .putExtra("TIME", "14:30")
                            .putExtra("LOCATION", "USC")
                            .putExtra("DETAILS", "Sample event details")
            );

    @Test
    public void testEditEventFieldsPopulated() {
        onView(withId(R.id.eventNameEdit))
                .check(matches(withText("Sample Event")));
        onView(withId(R.id.eventDateEdit))
                .check(matches(withText("11/27/2024")));
        onView(withId(R.id.eventTimeEdit))
                .check(matches(withText("14:30")));
        onView(withId(R.id.locationEdit))
                .check(matches(withText("USC")));
        onView(withId(R.id.DetailsEdit))
                .check(matches(withText("Sample event details")));
    }
}
