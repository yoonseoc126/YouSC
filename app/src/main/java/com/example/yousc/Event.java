package com.example.yousc;

import java.util.ArrayList;
import java.util.List;

//TODO: add comment functionality
//event model class
public class Event {
    public String name;
    public String location;
    public String date;
    public String time;
    public String details;
    public Integer upvotes;
    public Integer downvotes;
    public List<Comment> comments;

    public Event() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public String getName () {
        return this.name;
    }
    public String getLocation () {
        return this.location;
    }

    public String getDetails () {
        return this.details;
    }

    public String getDate () {
        return this.date;
    }


    public String getTime() {
        return this.time;
    }

    public Integer getUpvotes () {
        return this.upvotes;
    }

    public Integer getDownvotes() {
        return this.downvotes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public Integer getNumComments() {
        if (comments == null) {
            return 0;
        }
        return comments.size();
    }



    public Event(String name, String location, String date, String time, String details, Integer upvotes, Integer downvotes) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.time = time;
        this.details = details;
        this.upvotes = 0;
        this.downvotes = 0;
        comments = new ArrayList<>();
    }
}
