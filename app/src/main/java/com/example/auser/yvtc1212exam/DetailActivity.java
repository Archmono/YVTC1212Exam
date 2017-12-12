package com.example.auser.yvtc1212exam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DetailActivity extends AppCompatActivity {

    WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String name = bundle.getString("NAME");

        setTitle(name);
        wv = (WebView)findViewById(R.id.webView);
        String url = getIntent().getStringExtra("url");
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient());
        wv.loadUrl(url);
    }
}
