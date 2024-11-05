package com.example.yousc;

public class User {
    public String email;
    public String password;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email, String password) {
        //TODO: do we need to encrypt passwords
        this.email = email;
        this.password = password;
    }

}
