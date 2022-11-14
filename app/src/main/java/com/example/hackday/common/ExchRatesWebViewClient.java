package com.example.hackday.common;

import android.os.Looper;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ExchRatesWebViewClient extends WebViewClient {
    @Override
    public void onPageFinished(WebView view, String url) {
        view.evaluateJavascript("setExchRates('FFFFFFFF', 'btc', '0.123456789', '1.11111111');", s -> {
            Log.d("WebView", "Exchange Rates Page successfully loaded");

            //TODO  Here must be initialized the requesting of the exchange rates
        });
    }
}
