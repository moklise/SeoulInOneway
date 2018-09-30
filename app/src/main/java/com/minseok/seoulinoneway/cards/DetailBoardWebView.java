package com.minseok.seoulinoneway.cards;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.minseok.seoulinoneway.R;

public class DetailBoardWebView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailbaord_webview);

        WebView webView = (WebView) findViewById(R.id.detailBoard_webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.sijangtong.or.kr/nation.do");
        webView.setWebViewClient(new WebViewClient());

    }
}