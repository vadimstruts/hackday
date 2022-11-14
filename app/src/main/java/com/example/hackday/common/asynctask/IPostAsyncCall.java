package com.example.hackday.common.asynctask;

public interface IPostAsyncCall<TParam> {
    void onComplete(TParam result);
    void onError(String error);
}
