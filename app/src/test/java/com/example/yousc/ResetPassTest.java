//package com.example.yousc;
//
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.assertFalse;
//
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//import androidx.test.rule.ActivityTestRule;
//
//import com.example.yousc.ui.ForgotPasswordActivity;
//
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//@RunWith(AndroidJUnit4.class)
//public class PasswordResetTest {
//
//    @Rule
//    public ActivityTestRule<ForgotPasswordActivity> activityRule =
//            new ActivityTestRule<>(ForgotPasswordActivity.class);
//
//    @Test
//    public void testValidEmailTriggersPasswordReset() {
//        ForgotPasswordActivity activity = activityRule.getActivity();
//
//        // Input a valid email for password reset
//        String validEmail = "testuser@example.com";
//        activity.runOnUiThread(() -> activity.emailInputField.setText(validEmail));
//
//        // Simulate clicking the "Reset Password" button
//        activity.runOnUiThread(() -> activity.resetPasswordButton.performClick());
//
//        // Assert that the method to send a password reset email is called
//        boolean emailSent = activity.isEmailSent(); // Mock or actual method to check email sending
//        assertTrue("Password reset email should be sent for a valid email address.", emailSent);
//    }