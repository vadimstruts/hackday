package com.example.hackday.common;

import android.app.Activity;
import android.app.Instrumentation;
import android.provider.Browser;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class PermissionFileModuleTest {

    @Test
    public void requestPermissinIfNotGranted(){
        final Instrumentation.ActivityMonitor mBrowserActivityMonitor;

        mBrowserActivityMonitor = new Instrumentation.ActivityMonitor(Browser.class.getName(), null, false);
        InstrumentationRegistry.getInstrumentation().addMonitor(mBrowserActivityMonitor);

        Activity activity = mBrowserActivityMonitor.waitForActivityWithTimeout(5 * 1000);
        Assert.assertNotNull("Activity was not started", activity);
    }

}
