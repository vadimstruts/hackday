package com.example.hackday.common.asynctask;

public interface IParametrizedCallback<TParam> {
    void Call(TParam param);
}
