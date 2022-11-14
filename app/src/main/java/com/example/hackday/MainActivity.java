package com.example.hackday;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hackday.common.ExchRatesWebViewClient;
import com.example.hackday.common.asynctask.IPostAsyncCall;
import com.example.hackday.common.rest.ApiRequestMgr;
import com.example.hackday.common.rest.GetExcRatesRespParser;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    final static String AssetHtmlPagePath = "file:///android_asset/exchrates-page.html";
    final static int ApiRequestsInterval = 10000;

    WebView webViewExcRts;
    ApiRequestMgr apiRequestMgr;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiRequestMgr = new ApiRequestMgr(getApplicationContext());

        webViewExcRts = findViewById(R.id.webViewExchRts);
        webViewExcRts.loadUrl(AssetHtmlPagePath);

        webViewExcRts.getSettings().setJavaScriptEnabled(true);

        webViewExcRts.setWebViewClient(
                new ExchRatesWebViewClient(
                        getApplicationContext(),
                        this::OnWebViewInitialLoaded)
        );
    }

    private void OnWebViewInitialLoaded(){
        apiRequestMgr.GetExchangeRates(new IPostAsyncCall<String>() {
            @Override
            public void onComplete(String result) {

                try {
                    JSONArray jsonResult = new JSONArray(result);

                    for(int i=0; i < jsonResult.length(); i++) {
                        JSONObject object = jsonResult.getJSONObject(i);

                        final String currName = object.getString( GetExcRatesRespParser.RespInternalCurrencyProp);
                        final String usRate = object.getString(GetExcRatesRespParser.RespInternalUsRateProp);
                        final String date = object.getString(GetExcRatesRespParser.RespInternalDateProp);

                        webViewExcRts.evaluateJavascript(
                                String.format("setExchRates('%s', '%s', '%s');", currName, usRate, date),
                                null);
                    }
                }
                catch(Exception e){
                    final String err = String.format(getString(R.string.exc_on_complete_finished_with_error), e);
                    Log.e("MainActivity", err);

                    Toast.makeText(getApplicationContext(), err, Toast.LENGTH_LONG).show();
                }

                requestNextIterationIn10Sec();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getApplicationContext(), String.format(getString(R.string.exch_rate_retrieving_error), error), Toast.LENGTH_LONG).show();

                requestNextIterationIn10Sec();
            }
        });
    }

    private void requestNextIterationIn10Sec(){
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> webViewExcRts.evaluateJavascript("showProgress(true);",
                        s -> OnWebViewInitialLoaded()),
                ApiRequestsInterval);
    }
}