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
public class EmptyForgotPassTest {

    @Rule
    public ActivityScenarioRule<ForgotPassword> activityRule = new ActivityScenarioRule<>(ForgotPassword.class);

    @Test
    public void testEmptyForgotPassRedirect() {

        onView(withId(R.id.edit_email))
                .perform(typeText(""));

        onView(withId(R.id.send_email_button)).perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Test that the "Discover" map page has rendered
        onView(withId(R.id.forgot_password_banner)).check(matches(isDisplayed()));
    }
}
