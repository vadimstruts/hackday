package com.example.hackday.common;

import android.content.Context;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.hackday.R;
import com.example.hackday.common.asynctask.ISimpleCallback;

public class ExchRatesWebViewClient extends WebViewClient {

    private final Context appContext;
    private final ISimpleCallback onCompleteCallback;
    public ExchRatesWebViewClient(Context context, ISimpleCallback onCompleteCallback){
        appContext = context;
        this.onCompleteCallback = onCompleteCallback;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        try {
            onCompleteCallback.Call();
        } catch (Exception e) {
            final String err = String.format(appContext.getString(R.string.webview_page_loading_error), e);
            Log.e("WebView", err);
            Toast.makeText(appContext, err, Toast.LENGTH_LONG).show();
        }
    }
}
