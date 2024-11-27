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

@RunWith(AndroidJUnit4.class)
public class EmptyLoginTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testEmptyEmailLoginRedirect() {

        onView(withId(R.id.emailedit))
                .perform(typeText(""));
        onView(withId(R.id.passedit))
                .perform(typeText("yoyo"));

        onView(withId(R.id.signIn)).perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Test that the "Discover" map page has rendered
        onView(withId(R.id.welcome)).check(matches(isDisplayed())); }


    @Test
    public void testEmptyPassLoginRedirect() {

        onView(withId(R.id.emailedit))
                .perform(typeText("tester@usc.edu"));
        onView(withId(R.id.passedit))
                .perform(typeText(""));

        onView(withId(R.id.signIn)).perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Test that the "Discover" map page has rendered
        onView(withId(R.id.welcome)).check(matches(isDisplayed())); }
}
