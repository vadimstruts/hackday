package com.example.hackday.common.asynctask;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public abstract class TaskAsyncBase<TResult> {

    final Executor executor = Executors.newSingleThreadExecutor();
    final Handler uiSafeHandler = new Handler(Looper.getMainLooper());

    protected void ExecuteAsync(Callable<TResult> asyncWork, IPostAsyncCall<TResult> postAsyncCall){
        executor.execute(()->{
            try {
                if(null == asyncWork)
                    return;

                final TResult res = asyncWork.call();

                uiSafeHandler.post(() -> {
                    if(null == postAsyncCall)
                        return;

                    postAsyncCall.onComplete(res);
                });
            }catch(Exception e){
                uiSafeHandler.post(() -> {
                    if(null == postAsyncCall)
                        return;

                    postAsyncCall.onError(e.toString());
                });
            }
        });
    }

}
