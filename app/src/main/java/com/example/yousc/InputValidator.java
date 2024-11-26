package com.example.yousc;

import android.text.TextUtils;

public class InputValidator {
    public String checkEmptyLogin(String email, String password){
        if(email == null || email.isEmpty())
        {
            return "Please enter your email";
        }else if(password == null || password.isEmpty())
        {
            return "Please enter your password";
        }else
        {
            return null;
        }
    }
}
