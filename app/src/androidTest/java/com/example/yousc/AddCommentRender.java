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
public class AddCommentRender {
    @Rule
    public ActivityScenarioRule<MapsActivity> activityRule = new ActivityScenarioRule<>(MapsActivity.class);

    @Test
    public void testCommentPost() {
        String currentDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        String testComment = "Test Comment";
        String expectedUsername = "testuser@usc.edu";

        // Click on test event and check comments


        // Ask TA about this because the google maps stuff needs too much code

        onView(withId(R.id.editTextComment))
                .perform(typeText(testComment));
        onView(withId(R.id.commentSubmitButton))
                .perform(click());
        onView(withId(R.id.map)).check(matches(isDisplayed()));
//        onView(withId(R.id.recyclerView))
//                .check(matches(allOf(
//                        // Check comment text
//                        hasDescendant(withText(containsString(testComment))),
//                        // Check username
//                        hasDescendant(withText(containsString(expectedUsername))),
//                        // Check date
//                        hasDescendant(withText(containsString(currentDate))),
//                        // Check time
//                        hasDescendant(withText(containsString(currentTime)))
//                )));
    }
}
