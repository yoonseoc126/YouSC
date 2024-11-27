package com.example.yousc;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.intent.Intents;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class GoBackTest {

    @Rule
    public ActivityScenarioRule<ForgotPassword> activityRule =
            new ActivityScenarioRule<>(ForgotPassword.class);

    @Test
    public void testGoBackRedirect()
    {
        //add event button being displayed
        onView(withId(R.id.go_back_button)).check(matches(isDisplayed()));

        //click on add event button
        onView(withId(R.id.go_back_button)).perform(click());
        onView(withId(R.id.welcome)).check(matches(isDisplayed()));

    }
}

