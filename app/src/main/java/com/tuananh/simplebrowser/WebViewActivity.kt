package com.tuananh.simplebrowser

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.tuananh.simplebrowser.databinding.ActivityWebViewBinding

class WebViewActivity: AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initWebView()
    }

    override fun onBackPressed() {
        if (binding.myWebView.canGoBack()) {
            binding.myWebView.goBack()
        }
        else {
            super.onBackPressed()
        }
    }

    private fun initWebView() {
        with(binding.myWebView) {
            loadUrl(intent.getStringExtra(URL_KEY) ?: "")
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
        }
    }

    companion object {
        const val URL_KEY = "URL_KEY"
    }
}