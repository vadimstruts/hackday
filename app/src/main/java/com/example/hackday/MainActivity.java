package com.example.hackday;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.hackday.common.ExchRatesWebViewClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webViewExchRts = findViewById(R.id.webViewExchRts);
        webViewExchRts.loadUrl("file:///android_asset/exchrates-page.html");

        webViewExchRts.getSettings().setJavaScriptEnabled(true);
        webViewExchRts.setWebViewClient(new ExchRatesWebViewClient());
    }
}