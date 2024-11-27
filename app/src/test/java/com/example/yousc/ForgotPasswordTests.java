package com.example.yousc;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

// DONE
@RunWith(RobolectricTestRunner.class)
public class ForgotPasswordTests {
    private ForgotPassword testAccount;

    @Before
    public void setUp() {
        testAccount = new ForgotPassword();
    }

    @Test
    public void forgotPasswordEmptyPassword() {
        String testEmail = "";
        boolean validParameters = testAccount.validateEmail(testEmail);
        assertFalse("Empty email should be invalid", validParameters);
    }
}
