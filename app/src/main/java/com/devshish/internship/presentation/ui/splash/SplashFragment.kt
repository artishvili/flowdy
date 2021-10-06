package com.devshish.internship.presentation.ui.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.devshish.internship.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val viewModel: SplashViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {
            navigateToAuth.observe(viewLifecycleOwner) {
                val action = SplashFragmentDirections.actionSplashFragmentToAuthFragment()
                findNavController().navigate(action)
            }

            navigateToApp.observe(viewLifecycleOwner) {
                val action = SplashFragmentDirections.actionSplashFragmentToHomeFragment()
                findNavController().navigate(action)
            }
        }
    }
}
