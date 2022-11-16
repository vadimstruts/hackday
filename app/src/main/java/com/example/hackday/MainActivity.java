package com.example.hackday;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hackday.common.ExchRatesWebViewClient;
import com.example.hackday.common.PermissionFileModule;
import com.example.hackday.common.asynctask.IPostAsyncCall;
import com.example.hackday.common.asynctask.ProcessExchRateNativelyAsync;
import com.example.hackday.common.asynctask.TerminatePoolAsync;
import com.example.hackday.common.logs.SdLogger;
import com.example.hackday.common.rest.ApiRequestMgr;
import com.example.hackday.common.rest.GetExcRatesRespParser;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    final static String AssetHtmlPagePath = "file:///android_asset/exchrates-page.html";
    final static int ApiRequestsInterval = 10000;

    WebView webViewExcRts;
    ApiRequestMgr apiRequestMgr;
    TextView textViewExchRts;
    final ProcessExchRateNativelyAsync processExchRateNatively = new ProcessExchRateNativelyAsync();
    PermissionFileModule permissionFileModule;
    boolean isStoppedState;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isStoppedState = false;


        textViewExchRts = findViewById(R.id.textViewExchRts);
        textViewExchRts.setMovementMethod(new ScrollingMovementMethod());

        apiRequestMgr = new ApiRequestMgr(getApplicationContext());

        permissionFileModule = new PermissionFileModule(this);

        permissionFileModule.checkAndGrantPermission((result) -> initializeWebView());
    }

    private void OnWebViewInitialLoaded(){
        apiRequestMgr.GetExchangeRates(new IPostAsyncCall<String>() {
            @Override
            public void onComplete(String result) {

                StringBuilder tvLogLine = new StringBuilder();
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

                        tvLogLine.append(String.format("%s: %s, ", currName, usRate));

                        processExchRateNatively.ExecuteAsync(currName, usRate, (logString) -> SdLogger.getInstance().AppendLine(logString), null);
                    }

                    textViewExchRts.append(String.format("%s\r\n", tvLogLine));
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
        if(isStoppedState)
            return;

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> webViewExcRts.evaluateJavascript("showProgress(true);",
                        s -> OnWebViewInitialLoaded()),
                ApiRequestsInterval);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity", "onResume");

        permissionFileModule.checkAndGrantPermission((result) -> {
            Log.d("PermissionFileModule", "onResume " + isStoppedState);
            if(isStoppedState) {
                isStoppedState = false;
                initializeWebView();
                apiRequestMgr.Resume();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity", "onStop");
        isStoppedState = true;
        new TerminatePoolAsync().ExecuteAsync();
        apiRequestMgr.Shutdown();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initializeWebView(){
        Log.d("PermissionFileModule", "initializeWebView");
        webViewExcRts = findViewById(R.id.webViewExchRts);
        webViewExcRts.loadUrl(AssetHtmlPagePath);

        webViewExcRts.getSettings().setJavaScriptEnabled(true);

        webViewExcRts.setWebViewClient(
                new ExchRatesWebViewClient(
                        getApplicationContext(),
                        this::OnWebViewInitialLoaded)
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        isStoppedState = true;

        permissionFileModule.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}