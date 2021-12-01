package com.devshish.internship.presentation.ui.web

import android.net.Uri
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.devshish.internship.BuildConfig

class GeniusAuthWebViewClient(
    private val onCodeExtracted: (String) -> Unit
) : WebViewClient() {

    override fun shouldOverrideUrlLoading(
        view: WebView?,
        request: WebResourceRequest?
    ): Boolean {
        return isRequestValid(request?.url)
    }

    private fun isRequestValid(uri: Uri?): Boolean {
        val state = uri?.getQueryParameter("state")
        val code = uri?.getQueryParameter("code")

        return if (state ==  BuildConfig.GENIUS_REQUEST_STATE && code != null) {
            onCodeExtracted(code)
            true
        } else {
            false
        }
    }
}
