package com.example.yousc;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

import org.junit.Test;

public class AddEventDateValidateTest {
    @Test
    public void testAddEventDateValidator(){
        InputValidator test = new InputValidator();

        String validDate = "10/02/2050";
        String validTime = "02:00";
        String outdatedDate = "10/02/1991";

        assertNull(test.checkDateInFuture(validDate,validTime));

        assertEquals("Event is not in the future.", test.checkDateInFuture(outdatedDate, validTime));
    }
}
