package com.example.yousc;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.widget.TextView;

import androidx.test.espresso.Espresso;
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
public class LikeCountTest {

    public ActivityScenarioRule<MapsActivity> activityRule =
            new ActivityScenarioRule<>(MapsActivity.class);

    private int getTextAsInt(int viewId) {
        final int[] count = {0}; // Use an array to capture the value
        onView(withId(viewId)).check((view, noViewFoundException) -> {
            try
            {
                String text = ((TextView) view).getText().toString();
                count[0] = Integer.parseInt(text);
            }
            catch (NumberFormatException e)
            {
                count[0] = 0;
            }

        });
        return count[0];
    }


    @Test
    public void testUpvoteIncrementsCount() {
        Espresso.onIdle();
        int initialUpvote = getTextAsInt(R.id.upvoteCount);

        onView(withId(R.id.checkButton)).perform(click());

        // Simulate displaying these initial counts
        onView(withId(R.id.upvoteCount)).check(matches(withText(String.valueOf(initialUpvote + 1))));


    }

    @Test
    public void testUpvoteSecondClick() {
        // Capture initial counts
        Espresso.onIdle();

        int initialUpvotes = getTextAsInt(R.id.upvoteCount);

        // Perform first click to upvote
        onView(withId(R.id.checkButton)).perform(click());

        // Assert upvote incremented by 1
        onView(withId(R.id.upvoteCount)).check(matches(withText(String.valueOf(initialUpvotes + 1))));

        // Perform second click to toggle off the upvote
        onView(withId(R.id.checkButton)).perform(click());

        // Assert upvote count is back to the original value
        onView(withId(R.id.upvoteCount)).check(matches(withText(String.valueOf(initialUpvotes))));
    }

    @Test
    public void testSwitchFromUpvoteToDownvote() {
        // Capture initial counts
        Espresso.onIdle();

        int initialUpvotes = getTextAsInt(R.id.upvoteCount);
        int initialDownvotes = getTextAsInt(R.id.downvoteCount);

        // Perform click to upvote
        onView(withId(R.id.checkButton)).perform(click());

        // Assert upvote incremented by 1
        onView(withId(R.id.upvoteCount)).check(matches(withText(String.valueOf(initialUpvotes + 1))));
        onView(withId(R.id.downvoteCount)).check(matches(withText(String.valueOf(initialDownvotes))));

        // Perform click to downvote
        onView(withId(R.id.xButton)).perform(click());

        // Assert upvote decremented back to the original value, and downvote incremented by 1
        onView(withId(R.id.upvoteCount)).check(matches(withText(String.valueOf(initialUpvotes))));
        onView(withId(R.id.downvoteCount)).check(matches(withText(String.valueOf(initialDownvotes + 1))));
    }
}