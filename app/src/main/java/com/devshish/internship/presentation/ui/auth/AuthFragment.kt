package com.devshish.internship.presentation.ui.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.devshish.internship.BuildConfig.BASE_URL
import com.devshish.internship.R
import com.devshish.internship.data.repository.CLIENT_ID
import com.devshish.internship.databinding.FragmentAuthBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthFragment : Fragment(R.layout.fragment_auth) {

    private val binding by viewBinding(FragmentAuthBinding::bind)
    private val viewModel: AuthViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {
            binding.btnSignUp.setOnClickListener {
                requestAuth.observe(viewLifecycleOwner) {
                    val browserIntent = Intent(Intent.ACTION_VIEW, it.toUri())
                    startActivity(browserIntent)
                }
            }

            /*binding.btnGetToken.setOnClickListener {
                requestToken.observe(viewLifecycleOwner) {
                    binding.tvToken.text = it
                }
            }*/
        }
    }
}
