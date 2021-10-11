package com.devshish.internship.presentation.ui.profile

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentProfileBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel: ProfileViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        navigateForwardEvent.collect {
                            val action = ProfileFragmentDirections
                                .actionProfileFragmentToEditProfileFragment()
                            findNavController().navigate(action)
                        }
                    }
                }
            }

            userData.observe(viewLifecycleOwner) {
                binding.apply {
                    Glide.with(this@ProfileFragment)
                        .load(it.background)
                        .placeholder(R.drawable.ic_profile)
                        .into(ivBackgroundPicture)

                    Glide.with(this@ProfileFragment)
                        .load(it.photo)
                        .placeholder(R.drawable.ic_profile)
                        .into(ivProfilePicture)

                    tvNickname.text = it.nickname
                    tvEmail.text = it.email
                }
            }
        }
    }

    /*private fun TextView.checkAndSetText(data: String?, resourceId: Int) {
        val checkedData = data ?: getString(R.string.data_not_chosen)
        text = getString(resourceId, checkedData)
    }*/
}
