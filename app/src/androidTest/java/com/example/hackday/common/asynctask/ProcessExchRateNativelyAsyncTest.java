package com.example.hackday.common.asynctask;

import static org.junit.Assert.assertTrue;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@RunWith(AndroidJUnit4.class)
public class ProcessExchRateNativelyAsyncTest {

    private final CountDownLatch lock = new CountDownLatch(1);

    @Test
    public void callNativeModule() throws InterruptedException {

        final String currName = "BTC";
        final String usRate = "99999.999999999";

        ProcessExchRateNativelyAsync nativeCall = new ProcessExchRateNativelyAsync();

        AtomicBoolean isLogStringGenerated = new AtomicBoolean(false);
        nativeCall.ExecuteAsync(currName, usRate, (logString) -> {
            isLogStringGenerated.set(true);
            assertTrue(String.format("Log string %s must contain currency name: %s", logString, currName), logString.indexOf(currName) > 0);
            assertTrue(String.format("Log string %s must contain currency rate name: %s", logString, usRate), logString.indexOf(usRate) > 0);
        }, null);

        //noinspection ResultOfMethodCallIgnored
        lock.await(2000, TimeUnit.MILLISECONDS);

        assertTrue("Log string mus be returned", isLogStringGenerated.get());
    }
}
