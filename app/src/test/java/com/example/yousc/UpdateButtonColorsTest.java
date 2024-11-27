package com.example.yousc;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.example.yousc.Event;
import com.example.yousc.voteActivity;

@RunWith(RobolectricTestRunner.class)
public class UpdateButtonColorsTest {

    private voteActivity activity;
    private Event mockEvent;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(voteActivity.class).create().get();

        mockEvent = mock(Event.class);

        activity.event = mockEvent;

        activity.upvoteButt = new Button(activity);
        activity.downvoteButt = new Button(activity);
    }

    @Test
    public void testUpdateButtColors_UserHasUpvoted() {
        when(mockEvent.userHasUpvoted).thenReturn(true);
        when(mockEvent.userHasDownvoted).thenReturn(false);

        activity.updateButtColors();

        assertButtonColor(activity.upvoteButt, Color.WHITE);
        assertButtonColor(activity.downvoteButt, Color.GRAY);
    }

    @Test
    public void testUpdateButtColors_UserHasDownvoted() {
        when(mockEvent.userHasUpvoted).thenReturn(false);
        when(mockEvent.userHasDownvoted).thenReturn(true);

        activity.updateButtColors();

        assertButtonColor(activity.downvoteButt, Color.RED);
        assertButtonColor(activity.upvoteButt, Color.GRAY);
    }

    @Test
    public void testUpdateButtColors_NeutralState() {
        when(mockEvent.userHasUpvoted).thenReturn(false);
        when(mockEvent.userHasDownvoted).thenReturn(false);

        activity.updateButtColors();

        assertButtonColor(activity.upvoteButt, Color.GRAY);
        assertButtonColor(activity.downvoteButt, Color.GRAY);
    }

    private void assertButtonColor(Button button, int expectedColor) {
        ColorDrawable colorDrawable = (ColorDrawable) button.getBackground();
        assertEquals(expectedColor, colorDrawable.getColor());
    }
}
