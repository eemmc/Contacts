package com.hbb.contacts.core.ui.activity

import android.annotation.SuppressLint
import android.content.pm.ApplicationInfo
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.webkit.*
import androidx.activity.OnBackPressedCallback
import androidx.webkit.WebViewClientCompat
import androidx.webkit.WebViewCompat
import androidx.webkit.WebViewFeature
import com.hbb.contacts.core.databinding.ActivityBrowserBinding
import com.hbb.contacts.core.tools.AndroidUtils
import com.hbb.contacts.core.ui.base.BaseActivity

class BrowserActivity : BaseActivity() {
    private lateinit var binding: ActivityBrowserBinding

    private val mOnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (binding.webView.canGoBack()) {
                binding.webView.goBack()
            } else if (isEnabled) {
                this.isEnabled = false
                requireActivity().onBackPressed()
            }
        }
    }

    private val mWebViewClient = object : WebViewClientCompat() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            if (!TextUtils.isEmpty(url)) {
                binding.title.text = url
            }
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            binding.close.isEnabled = view?.canGoBack() == true
            //
            val title = view?.title
            if (!TextUtils.isEmpty(title)) {
                binding.title.text = title
            }
        }
    }

    private val mWebChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
        }

        override fun onShowFileChooser(
            webView: WebView?,
            filePathCallback: ValueCallback<Array<Uri>>?,
            fileChooserParams: FileChooserParams?
        ): Boolean {
            return super.onShowFileChooser(
                webView,
                filePathCallback,
                fileChooserParams
            )
        }
    }


    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBrowserBinding.inflate(layoutInflater)
        super.setContentView(binding.root)
        super.getOnBackPressedDispatcher().addCallback(
            this, mOnBackPressedCallback
        )
        //
        binding.close.isEnabled = false
        binding.more.isEnabled = false
        binding.close.setOnClickListener { finish() }
        binding.back.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        //
        val settings = binding.webView.settings
        settings.javaScriptEnabled = true
        settings.allowFileAccess = true
        settings.allowContentAccess = true
        settings.domStorageEnabled = true
        settings.databaseEnabled = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.defaultTextEncodingName = "UTF-8"
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        //
        binding.webView.webViewClient = mWebViewClient
        binding.webView.webChromeClient = mWebChromeClient
        if (applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0) {
            WebView.setWebContentsDebuggingEnabled(true)
        }
        //
        bindJavascriptInterface(
            binding.webView,
            JS_OBJECT_NAME,
            INTERRUPTED_RULES
        ) {
            Log.e("DEBUG", "message: $it")
        }
        //
        intent.dataString?.let {
            binding.webView.loadUrl(it)
        }
    }

    override fun onResume() {
        binding.webView.onResume()
        binding.webView.resumeTimers()
        super.onResume()
    }

    override fun onPause() {
        binding.webView.onPause()
        binding.webView.pauseTimers()
        AndroidUtils.hideSoftInput(window)
        super.onPause()
    }


    companion object {
        private const val JS_OBJECT_NAME = "app"
        private val INTERRUPTED_RULES: Set<String> = setOf(
            // "https://foobar.com:8088"
            // "https://www.example.com"
            // "https://*.example.com"
            // "https://*.example.com:8088"
            // "https://127.0.0.1"
            // "https://[::1]"
            // "https://[0:0::1]"
            // "https://[::1]:99"
            // "my-app-scheme://",
            "*"
        )

        @Suppress("SameParameterValue")
        private fun bindJavascriptInterface(
            view: WebView,
            name: String,
            rules: Set<String>,
            listener: (message: String) -> Unit
        ) {
            if (WebViewFeature.isFeatureSupported(WebViewFeature.WEB_MESSAGE_LISTENER)) {
                WebViewCompat.addWebMessageListener(
                    view, name, rules
                ) { _, message, _, _, _ -> listener(message.data!!) }
            } else {
                view.addJavascriptInterface(object {
                    @Suppress("UNUSED")
                    @JavascriptInterface
                    fun postMessage(message: String) {
                        view.handler.post {
                            listener(message)
                        }
                    }
                }, name)
            }
        }
    }

}
