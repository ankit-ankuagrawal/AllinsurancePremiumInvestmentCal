package com.example.allinsurancepremiuminvestmentcalculator;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented general_type_insurance_layout, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under general_type_insurance_layout.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.allinsurancepremiuminvestmentcalculator", appContext.getPackageName());
    }
}
