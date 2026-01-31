package com.tuananh.simplebrowser

import android.webkit.WebViewClient
import com.tuananh.simplebrowser.databinding.ActivityWebViewBinding
import android.app.DownloadManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.view.isVisible
import android.os.Environment
import android.webkit.WebView

class WebViewActivity: BaseActivity() {
    private lateinit var binding: ActivityWebViewBinding

    override fun initLayout() {
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

    override fun onPause() {
        super.onPause()
        pauseWebAudio()
    }

    private fun initWebView() {
        with(binding.myWebView) {
            loadUrl(intent.getStringExtra(URL_KEY) ?: "")
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            addJavascriptInterface(WebInterface(), "AndroidAudio")
            webViewClient = object: WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    this@WebViewActivity.hideProgressBar()
                    evaluateJavascript(
                        "var audios = document.getElementsByTagName('audio');\n" +
                                "for (var i = 0; i < audios.length; i++) {\n" +
                                "  audios[i].addEventListener('play', function(event) {\n" +
                                "    var src = event.target.currentSrc;\n" +
                                "    // Send to Android using the injected interface\n" +
                                "    if (window.AndroidAudio && window.AndroidAudio.onAudioSource) {\n" +
                                "      window.AndroidAudio.onAudioSource(src);\n" +
                                "    }\n" +
                                "    // Also log for debugging:\n" +
                                "    console.log(\"Audio loaded:\", src);\n" +
                                "  });\n" +
                                "}"
                        , null)
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    this@WebViewActivity.showProgressBar()
                }
            }
        }
        binding.btnDownload.setOnClickListener {
            val url = binding.tvLink.text.toString()
            downloadAudio(url)
        }
        binding.btnMore.setOnClickListener {
            binding.layoutMore.isVisible = !binding.layoutMore.isVisible &&
                    !binding.tvLink.text.isEmpty()
        }
    }

    private fun downloadAudio(url: String) {
        val request = DownloadManager.Request(Uri.parse(url))
        request.setTitle(getString(R.string.text_audio_download))
        request.setDescription(getString(R.string.text_downloading_audio_file))
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC, AUDIO_FILE_NAME)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
    }

    private fun pauseWebAudio() {
        val js = "" +
                "(function(){" +
                "  document.querySelectorAll('audio').forEach(function(a) {" +
                "    try { a.pause(); a.currentTime = 0; } catch(e) {}" +
                "  });" +
                "  document.querySelectorAll('video').forEach(function(v) {" +
                "    try { v.pause(); v.currentTime = 0; } catch(e) {}" +
                "  });" +
                "})();"
        binding.myWebView.evaluateJavascript(js, null)
    }

    companion object {
        const val URL_KEY = "URL_KEY"
        const val AUDIO_FILE_NAME = "audio.mp3"
    }

    inner class WebInterface {
        @android.webkit.JavascriptInterface
        fun onAudioSource(src: String) {
            runOnUiThread {
                binding.layoutMore.isVisible = !src.isEmpty()
                binding.tvLink.text = src
            }
        }
    }
}