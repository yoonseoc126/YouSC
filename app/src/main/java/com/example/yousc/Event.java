package com.example.yousc;

//event model class
public class Event {
    public String name;
    public String location;
    public String time;
    public String details;
    public Integer upvotes;
    public Integer downvotes;

    public Event() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
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
