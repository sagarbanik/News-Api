package com.b1.sagar.newsapitest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DetailsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{


    WebView myWebView;
    String WEB_URL;

    SwipeRefreshLayout swiperRefLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        swiperRefLayout = findViewById(R.id.swiperRefLayout);
        swiperRefLayout.setRefreshing(true);
        swiperRefLayout.setOnRefreshListener(this::onRefresh);

        Intent intent = getIntent();

        WEB_URL = intent.getStringExtra("Web_Url");


        myWebView = findViewById(R.id.webView);

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        myWebView.loadUrl(WEB_URL);
    }

    @Override
    public void onRefresh() {
        myWebView.loadUrl(WEB_URL);
    }
}
