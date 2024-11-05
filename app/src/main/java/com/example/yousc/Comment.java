package com.example.yousc;

public class Comment {
    private final String username;
    private final String time;
    private final String text;

    public Comment(String username, String time, String text) {
        this.username = username;
        this.time = time;
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public String getTime() {
        return time;
    }

    public String getText() {
        return text;
    }
}