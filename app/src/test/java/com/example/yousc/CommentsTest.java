package com.example.yousc;


import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommentsTest {

    /* Tests the count # displayed in Comments (#) of the comments page */
    @Test
    public void testCommentsCount() {
        List<Comment> comments = new ArrayList<>(
                Arrays.asList(
                        new Comment("u1@gmail.com", "11:00PM", "This is a test comment."),
                        new Comment("u2@gmail.com", "12:00AM", "This is a test comment."),
                        new Comment("u3@gmail.com", "1:00AM", "This is a test comment.")
                )
        );
        CommentAdapter test = new CommentAdapter(comments, "user@gmail.com");

        assertEquals("Comments count must match size of the comment list.", comments.size(), test.getItemCount());
    }

}