package com.iamsmk.engage_webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.iamsmk.engage_webview.databinding.ActivityEngageWebviewBinding

class EngageWebviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEngageWebviewBinding
    private var webURL = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEngageWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        webURL = intent.extras?.getString("webURL").toString()

        setupWebView()
        WebView.setWebContentsDebuggingEnabled(true)

        binding.wvView.settings.apply {
            allowContentAccess = true
            javaScriptEnabled = true
            mediaPlaybackRequiresUserGesture = false
        }

        binding.wvView.webViewClient = WebViewClient()
        binding.wvView.webChromeClient = object : WebChromeClient() {
            override fun onPermissionRequest(request: PermissionRequest) {
                runOnUiThread {
                    if (request.resources.first() == PermissionRequest.RESOURCE_VIDEO_CAPTURE) {
                        request.grant(request.resources)
                    } else {
                        request.deny()
                    }
                }
            }
        }
    }

    private fun setupWebView() {
        binding.wvView.loadUrl(webURL)
    }
}