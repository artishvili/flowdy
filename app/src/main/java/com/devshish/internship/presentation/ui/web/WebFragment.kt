package com.devshish.internship.presentation.ui.web

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentWebBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class WebFragment : Fragment(R.layout.fragment_web) {

    private val binding by viewBinding(FragmentWebBinding::bind)
    private val viewModel: WebViewModel by viewModel()

    private val args: WebFragmentArgs by navArgs()

    private val client = GeniusAuthWebViewClient {
        viewModel.inputCode(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            webView.apply {
                settings.javaScriptEnabled = true
                webViewClient = client
                loadUrl(args.link)
            }
        }

        with(viewModel) {
            navigateForward.observe(viewLifecycleOwner) {
                val action = WebFragmentDirections.actionWebFragmentToHomeFragment()
                findNavController().navigate(action)
            }
        }
    }
}
