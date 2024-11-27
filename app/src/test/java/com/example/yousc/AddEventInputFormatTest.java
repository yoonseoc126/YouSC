package com.example.yousc;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

import org.junit.Test;

public class AddEventInputFormatTest {
    @Test
    public void testAddEventInputs(){
        InputValidator test = new InputValidator();

        // Empty field should get error message
        assertEquals("Please fill out all required forms", test.checkEmptyAddEvent(null, "location", "time", "details"));
        assertNull(test.checkEmptyAddEvent("event","location", "time", "details"));

        // Date not formatted with mm/dd/yyyy should get error message
        assertEquals("Date must be formatted in mm/dd/yyyy", test.checkDateFormat("2020/10/12"));
        assertNull(test.checkDateFormat("10/12/2020"));

        // Time not formatted with hh:mm should get error message
        assertEquals("Time must be formatted in hh:mm", test.checkTimeFormat("1:00"));
        assertNull(test.checkTimeFormat("01:00"));
    }
}
