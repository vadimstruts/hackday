package com.example.hackday.common.asynctask;

import java.util.concurrent.Callable;

public abstract class NativeAsyncCall<TResult> extends TaskAsyncBase<TResult> {

    static {
        System.loadLibrary("native-module");
    }

    @Override
    protected void ExecuteAsync(Callable<TResult> asyncWork, IPostAsyncCall<TResult> postAsyncCall) {
        super.ExecuteAsync(asyncWork, postAsyncCall);
    }
}
