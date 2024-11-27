package com.example.yousc;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.widget.TextView;

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
public class DislikeCountTest {

    public ActivityScenarioRule<voteActivity> activityRule =
            new ActivityScenarioRule<>(voteActivity.class);

    private int getTextAsInt(int viewId) {
        final int[] count = {0}; // Use an array to capture the value
        onView(withId(viewId)).check((view, noViewFoundException) -> {
            String text = ((TextView) view).getText().toString();
            count[0] = Integer.parseInt(text);
        });
        return count[0];
    }


    @Test
    public void testDownvoteIncrementsCount() {

        int initialDownvote = getTextAsInt(R.id.downvoteCount);

        onView(withId(R.id.xButton)).perform(click());

        // Simulate displaying these initial counts
        onView(withId(R.id.downvoteCount)).check(matches(withText(String.valueOf(initialDownvote + 1))));


    }

    @Test
    public void testDownvoteSecondClick() {
        // Capture initial counts
        int initialDownvotes = getTextAsInt(R.id.downvoteCount);

        // Perform first click to upvote
        onView(withId(R.id.xButton)).perform(click());

        // Assert upvote incremented by 1
        onView(withId(R.id.downvoteCount)).check(matches(withText(String.valueOf(initialDownvotes + 1))));

        // Perform second click to toggle off the upvote
        onView(withId(R.id.xButton)).perform(click());

        // Assert upvote count is back to the original value
        onView(withId(R.id.downvoteCount)).check(matches(withText(String.valueOf(initialDownvotes))));
    }

    @Test
    public void testSwitchFromDownvoteToUpvote() {
        // Capture initial counts
        int initialUpvotes = getTextAsInt(R.id.upvoteCount);
        int initialDownvotes = getTextAsInt(R.id.downvoteCount);

        // Perform click to downvote
        onView(withId(R.id.xButton)).perform(click());

        // Assert downvote incremented by 1
        onView(withId(R.id.downvoteCount)).check(matches(withText(String.valueOf(initialDownvotes + 1))));
        onView(withId(R.id.upvoteCount)).check(matches(withText(String.valueOf(initialUpvotes))));

        // Perform click to upvote
        onView(withId(R.id.checkButton)).perform(click());

        // Assert downvote decremented back to the original value, and upvote incremented by 1
        onView(withId(R.id.downvoteCount)).check(matches(withText(String.valueOf(initialDownvotes))));
        onView(withId(R.id.upvoteCount)).check(matches(withText(String.valueOf(initialUpvotes + 1))));
    }

}