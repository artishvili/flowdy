package com.devshish.internship.presentation.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentProfileBinding
import com.devshish.internship.presentation.ui.utils.showSnackbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel: ProfileViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            ivLogout.setOnClickListener {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(getString(R.string.auth_sign_out))
                    .setMessage(getString(R.string.auth_sign_out_confirmation))
                    .setNegativeButton(getString(R.string.dialog_negative_button)) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setPositiveButton(getString(R.string.dialog_positive_button)) { dialog, _ ->
                        dialog.dismiss()
                        viewModel.logout()
                    }
                    .show()
            }
        }

        with(viewModel) {
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

            uiEvent.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { state ->
                    when (state) {
                        ProfileViewModel.UIEvent.NavigateToAuth -> {
                            val action =
                                ProfileFragmentDirections.actionProfileFragmentToAuthFragment()
                            findNavController().navigate(action)
                        }
                        is ProfileViewModel.UIEvent.NetworkError -> {
                            requireView().showSnackbar(
                                messageRes = R.string.snackbar_something_went_wrong,
                                action = Pair(R.string.snackbar_retry) { loadUser() }
                            )
                        }
                    }
                }
            }
        }
    }
}
