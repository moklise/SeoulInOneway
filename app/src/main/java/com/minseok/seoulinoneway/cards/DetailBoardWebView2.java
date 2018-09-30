package com.minseok.seoulinoneway.cards;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.minseok.seoulinoneway.R;

public class DetailBoardWebView2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailboard_webview2);

        WebView webView = (WebView) findViewById(R.id.detailBoard_webView2);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.naver.com"); //링크 바꿔야함!
        webView.setWebViewClient(new WebViewClient());

    }
}
