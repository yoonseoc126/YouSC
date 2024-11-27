package com.example.yousc;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

import org.junit.Test;

public class FryftValidationTest {
    @Test
    public void testFryftValidation(){
        // Address of Community Goods (outside Fryft)
        double outLatitude = -118.3636;
        double outLongitude = 34.0842;

        EventHelper test = new EventHelper();

        assertEquals("Event must be within USC Fryft zone", test.isWithinFryft(outLatitude, outLongitude));
        assertNull(test.isWithinFryft(34.0206,-118.2854));

    }
}
