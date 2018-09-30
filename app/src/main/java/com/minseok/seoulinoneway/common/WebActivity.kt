package com.minseok.seoulinoneway.common

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.minseok.seoulinoneway.R
import kotlinx.android.synthetic.main.activity_web.*


class WebActivity : ExtendedActivity() {
    lateinit var webView: WebView
    lateinit var progressBar: ProgressBar
    lateinit var btnBack: TextView
    lateinit var btnShare: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        val url = intent.getStringExtra("url")

        btnBack = findViewById(R.id.btn_back)
        btnBack.setOnClickListener { finish() }
        btnShare = findViewById(R.id.btn_share)
        btnShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT, url)
                type = "text/plain"
            }
            startActivity(sendIntent)
        }

        initWebView(url)
    }

    private fun initWebView(url: String) {
        webView = findViewById(R.id.webview)
        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(true)
        webView.settings.builtInZoomControls = true
        webView.loadUrl(url)

        progressBar = findViewById(R.id.progressbar)
        progressBar.max = 100
        progressBar.progress = 1

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {
                progressBar.progress = progress
            }
        }
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progressBar.visibility = View.VISIBLE
            }


            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {
                progressBar.visibility = View.GONE
                txt_title.text = webView.title
            }
        }
    }
}
