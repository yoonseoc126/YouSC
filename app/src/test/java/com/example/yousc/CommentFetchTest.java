package com.example.yousc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

@RunWith(RobolectricTestRunner.class)
public class CommentFetchTest {

    /*  IN PROGRESS */
    @Test
    public void testCommentFetchFormat() {
        String testEventName = "Test Event";
        String testLocation = "3201 S Hoover St, Los Angeles, CA 90007";
        String testDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
        String testTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        String testDetails = "Test Details";
        String testEmail = "test@gmail.com";
        Event mockEvent = new Event(testEventName, testLocation, testDate, testTime, testDetails, 0, 0, testEmail);

        // Populate event with test comments
        List<Comment> testComments = new ArrayList<>();
        Comment c1 = new Comment("User1", "12:20", "This is test comment 1");
        Comment c2 = new Comment("User2", "12:30", "This is test comment 2");
        testComments.add(c1);
        testComments.add(c2);
        mockEvent.comments = testComments;

        // All comments should be well formed
        CommentsActivity test = new CommentsActivity();
        boolean validity = test.validCommentStruct(mockEvent);
        assertTrue("Valid, well-formed comments provided", validity);
    }
}
