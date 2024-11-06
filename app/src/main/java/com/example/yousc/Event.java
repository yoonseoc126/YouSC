package com.example.yousc;

//TODO: add comment functionality
//event model class
public class Event {
    public String name;
    public String location;
    public String time;
    public String details;
    public Integer upvotes;
    public Integer downvotes;
    // Marker Id that lets us track which marker

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

    public String getTime() {
        return this.time;
    }

    public Integer getUpvotes () {
        return this.upvotes;
    }

    public Integer getDownvotes() {
        return this.downvotes;
    }


    public Event(String name, String location, String time, String details, Integer upvotes, Integer downvotes) {
        this.name = name;
        this.location = location;
        this.time = time;
        this.details = details;
        this.upvotes = 0;
        this.downvotes = 0;
    }
}
