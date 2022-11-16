package com.example.hackday.common.asynctask;

public class ProcessExchRateNativelyAsync extends NativeAsyncCall<String> {

    public native String ProcessExchRateNatively(String currSymbol, String priceUsd);

    public void ExecuteAsync(String currSymbol, String priceUsd, IParametrizedCallback<String> asyncCall, IPostAsyncCall<String> postAsyncCall){
        ExecuteAsync(() -> {
            final String logString = ProcessExchRateNatively(currSymbol, priceUsd);

            if(null != asyncCall)
                asyncCall.Call(logString);

            return logString;
        }, postAsyncCall);
    }
}
