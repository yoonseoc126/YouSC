package com.example.yousc;

public class User {
    public String email;
    public String password;

    public User() {
        // Default constructor
    }

    public User(String email, String password) {
        //TODO: do we need to encrypt passwords
        this.email = email;
        this.password = password;
    }

}
