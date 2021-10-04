package com.devshish.internship.presentation.ui.auth

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentAuthBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthFragment : Fragment(R.layout.fragment_auth) {

    private val binding by viewBinding(FragmentAuthBinding::bind)
    private val viewModel: AuthViewModel by viewModel()

    private var link: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnSignUp.setOnClickListener { viewModel.requestAuthentication() }
        }

        with(viewModel) {
            requestAuth.observe(viewLifecycleOwner) {
                link = it
            }

            onAuthenticateClick.observe(viewLifecycleOwner) {
                val action = AuthFragmentDirections.actionAuthFragmentToWebFragment(link.toString())
                findNavController().navigate(action)
            }
        }
    }
}
