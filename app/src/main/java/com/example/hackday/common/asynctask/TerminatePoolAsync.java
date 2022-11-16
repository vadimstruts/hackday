package com.example.hackday.common.asynctask;

import com.example.hackday.common.logs.SdLogger;

public class TerminatePoolAsync extends TaskAsyncBase<Void>{

    public void ExecuteAsync(){
        ExecuteAsync(() -> {
            SdLogger.getInstance().ShutDown();
            return null;
        }, null);
    }

}
