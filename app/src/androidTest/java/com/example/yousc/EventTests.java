package com.example.yousc;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.openLinkWithText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class EventTests {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void testLoginRedirect() {
        Intents.init();
        onView(withId(R.id.emailedit))
                .perform(typeText("testuser@usc.edu"));
        onView(withId(R.id.passedit))
                .perform(typeText("test123"));
        onView(withId(R.id.signIn)).perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        intended(hasComponent(MapsActivity.class.getName()));

        onView(withId(R.id.addEventButton)).check(matches(isDisplayed()));

        Intents.release();
    }

    @Test
    public void testCommentPost() {
        String currentDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        String testComment = "Test Comment";
        String expectedUsername = "testuser@usc.edu";

        // Login with test user credentials
        onView(withId(R.id.emailedit))
                .perform(typeText("testuser@usc.edu"));
        onView(withId(R.id.passedit))
                .perform(typeText("test123"));
        onView(withId(R.id.signIn)).perform(click());

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

    @Test
    public void testDeleteEventError() {
//        String testEventName = "Test Event";
//
//        onView(withId(R.id.your_text_view_id))
//                .perform(openLinkWithText("45"));
//
//
//        onView(withId(R.id.signIn)).perform(click());
//
//        onView(withId(R.id.map)).check(matches(isDisplayed()));
    }

    @Test
    public void testDeleteEventSuccess() {

    }


}