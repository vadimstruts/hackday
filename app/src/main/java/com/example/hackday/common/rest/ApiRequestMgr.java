package com.example.hackday.common.rest;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hackday.R;
import com.example.hackday.common.asynctask.IPostAsyncCall;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class ApiRequestMgr{

    final static String ApiUrl = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?limit=5";
    final static String UserAgentHeader = "HackDay App";
    final static String AcceptHeader = "application/json";
    final static String ApiKey = "bdb3792c-9bde-4a91-837c-0216aab5814a";

    final RequestQueue queue;
    final Context context;

    final AtomicBoolean isStopped;

    public ApiRequestMgr(Context ctx){
        this.queue = Volley.newRequestQueue(ctx);
        context = ctx;
        isStopped = new AtomicBoolean(false);
    }

    public void GetExchangeRates(IPostAsyncCall<String> result){

        if(isStopped.get())
            return;

        final GetExcRatesRespParser parser = new GetExcRatesRespParser(context);

        final JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                ApiUrl,
                null,
                response -> parser.Parse(response, result),
                error -> {
                    final String err = String.format(context.getString(R.string.exch_rate_retrieving_error), error);
                    Log.e("GetExchangeRates", err);
                    result.onError(err);
                })
        {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String>  params = new HashMap<>();
                params.put("User-Agent", UserAgentHeader);
                params.put("Accept", AcceptHeader);
                params.put("X-CMC_PRO_API_KEY",  ApiKey);

                return params;
            }
        };

        queue.add(request);
    }

    public void Resume(){
        isStopped.set(false);
    }

    public void Shutdown(){
        isStopped.set(true);
    }
}
