package com.example.yousc;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;

public class EmptyLoginTest {
    @Test
    public void testEmptyLogin() {
        InputValidator test = new InputValidator();
        String nonEmptyEmail = "tester@usc.edu";
        String nonEmptyPass = "hihellohi";

        // Empty email should get email error message
        assertEquals("Please enter your email", test.checkEmptyLogin(null, nonEmptyPass));

        // Empty pass should get pass error message
        assertEquals("Please enter your password", test.checkEmptyLogin(nonEmptyEmail, null));

        // If neither empty, should return null
        assertNull(test.checkEmptyLogin(nonEmptyEmail, nonEmptyPass));

    }
}
