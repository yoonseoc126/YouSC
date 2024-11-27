package com.example.yousc;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

// DONE
@RunWith(RobolectricTestRunner.class)
public class SignUpTests {
        private createAccount testAccount;

        @Before
        public void setUp() {
            testAccount = new createAccount();
        }

        @Test
        public void testEmptyEmailSignUpParams() {
            String testEmail = "";
            String testPassword = "testPassword";
            boolean validParameters = testAccount.checkTextFields(testEmail, testPassword);
            assertFalse("Empty email should be invalid", validParameters);
        }

        @Test
        public void testEmptyPasswordSignUpParams() {
            String testEmail = "test@usc.edu";
            String testPassword = "";
            boolean validParameters = testAccount.checkTextFields(testEmail, testPassword);
            assertFalse("Empty password should be invalid", validParameters);
        }

        @Test
        public void testValidSignUpParams() {
            String testEmail = "test@usc.edu";
            String testPassword = "testPassword";
            boolean validParameters = testAccount.checkTextFields(testEmail, testPassword);
            assertTrue("Valid email and password should be accepted", validParameters);
        }

}