package com.example.yousc;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class IncrementVotesTest {

    public ActivityScenarioRule<voteActivity> activityRule = new ActivityScenarioRule<>(voteActivity.class);

    @Test
    public void validateUpvoteIncrementation() {
        // Initialize an Event object with specific values
        Event testEvent = new Event("Test Event", "USC Campus", "2024-11-25", "12:00 PM",
                "Details about the event", 10, 5, "author@example.com");

        // Set initial upvote and downvote values
        testEvent.upvotes = 10;
        testEvent.downvotes = 5;

        // Simulate an upvote click
        onView(withId(R.id.checkButton)).perform(click());

        // Validate the upvote increment logic
        int expectedUpvotes = testEvent.userHasUpvoted ? testEvent.upvotes : testEvent.upvotes + 1;
        int expectedDownvotes = testEvent.userHasDownvoted ? testEvent.downvotes - 1 : testEvent.downvotes;

        // Update the Event object state
        testEvent.upvote();

        // Check the UI reflects the updated state
        onView(withId(R.id.upvoteCount)).check(matches(withText(String.valueOf(expectedUpvotes))));
        onView(withId(R.id.downvoteCount)).check(matches(withText(String.valueOf(expectedDownvotes))));
    }

    @Test
    public void validateDownvoteIncrementation() {
        // Initialize an Event object with specific values
        Event testEvent = new Event("Test Event", "USC Campus", "2024-11-25", "12:00 PM",
                "Details about the event", 10, 5, "author@example.com");

        // Set initial upvote and downvote values
        testEvent.upvotes = 10;
        testEvent.downvotes = 5;

        // Simulate a downvote click
        onView(withId(R.id.xButton)).perform(click());

        // Validate the downvote increment logic
        int expectedDownvotes = testEvent.userHasDownvoted ? testEvent.downvotes : testEvent.downvotes + 1;
        int expectedUpvotes = testEvent.userHasUpvoted ? testEvent.upvotes - 1 : testEvent.upvotes;

        // Update the Event object state
        testEvent.downvote();

        // Check the UI reflects the updated state
        onView(withId(R.id.upvoteCount)).check(matches(withText(String.valueOf(expectedUpvotes))));
        onView(withId(R.id.downvoteCount)).check(matches(withText(String.valueOf(expectedDownvotes))));
    }


}