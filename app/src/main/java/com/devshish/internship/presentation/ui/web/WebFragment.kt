package com.devshish.internship.presentation.ui.web

import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentWebBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class WebFragment : Fragment(R.layout.fragment_web) {

    private val binding by viewBinding(FragmentWebBinding::bind)
    private val viewModel: WebViewModel by viewModel()

    private val args: WebFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            webView.apply {
                settings.javaScriptEnabled = true
                webViewClient = object : WebViewClient() {

                    @RequiresApi(Build.VERSION_CODES.N)
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean =
                        if (request?.isRedirect == true) {
                            val code = request.url.getQueryParameter("code")
                            viewModel.getToken(code!!)

                            val action = WebFragmentDirections.actionWebFragmentToHomeFragment()
                            findNavController().navigate(action)
                            true
                        } else false
                }
                loadUrl(args.link)
            }
        }
    }
}
