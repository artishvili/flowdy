package com.devshish.internship.presentation.ui.web

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.devshish.internship.databinding.ActivityWebBinding
import com.devshish.internship.presentation.ui.main.MainActivity
import com.devshish.internship.presentation.ui.main.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class WebActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityWebBinding::inflate)
    private val viewModel: WebViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val intent = intent
        val link = intent.getStringExtra("LINK")

        with(binding) {
            webView.apply {
                settings.javaScriptEnabled = true
                webViewClient = object : WebViewClient() {

                    @RequiresApi(Build.VERSION_CODES.N)
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        return if (request?.isRedirect == true) {
                            val code = request.url.getQueryParameter("code")
                            viewModel.getToken(code!!)
                            Intent(this@WebActivity, MainActivity::class.java).apply {
                                startActivity(this)
                            }
                            true
                        } else {
                            false
                        }
                    }
                }
                loadUrl(link!!)
            }
        }
    }
}
