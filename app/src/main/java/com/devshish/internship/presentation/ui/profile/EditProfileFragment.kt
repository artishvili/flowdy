package com.devshish.internship.presentation.ui.profile

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.devshish.internship.R
import com.devshish.internship.data.repository.ProfileRepository
import com.devshish.internship.databinding.FragmentEditProfileBinding
import com.devshish.internship.domain.model.User
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {

    private lateinit var binding: FragmentEditProfileBinding

    private val viewModel: EditProfileViewModel by viewModel()

    private val getProfileImage: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri: Uri? ->
            imageUri?.let { viewModel.onProfileImagePick(it) }
        }

    private val getBackgroundImage: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri: Uri? ->
            imageUri?.let { viewModel.onBackgroundImagePick(it) }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditProfileBinding.bind(view)

        with(binding) {
            btnSaveChanges.setOnClickListener {
                viewModel.onSaveButtonClick(
                    nickname = etNickname.text.toString(),
                    country = etCountry.text.toString(),
                    city = etCity.text.toString(),
                    description = etDescription.text.toString()
                )
            }

            ivSelectPhoto.setOnClickListener { getProfileImage.launch("image/*") }
            ivSelectBackground.setOnClickListener { getBackgroundImage.launch("image/*") }
        }

        with(viewModel) {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.CREATED) {
                    launch { userFlow.collect { user -> showUser(user) } }
                    launch {
                        profilePictureUri.collect { uri ->
                            Glide.with(this@EditProfileFragment)
                                .load(uri)
                                .placeholder(R.drawable.ic_profile)
                                .into(binding.ivProfilePicture)
                        }
                    }
                    launch {
                        backgroundPictureUri.collect { uri ->
                            Glide.with(this@EditProfileFragment)
                                .load(uri)
                                .placeholder(R.drawable.ic_profile)
                                .into(binding.ivBackgroundPicture)
                        }
                    }
                    launch {
                        showSnackBarEvent.collect {
                            Snackbar.make(
                                binding.root,
                                R.string.fields_not_filled,
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
                    launch { navigateBackEvent.collect { findNavController().navigateUp() } }
                }
            }
        }
    }

    private fun showUser(user: User) {
        binding.apply {
            etNickname.setText(user.nickname)
            etCountry.setText(user.country)
            etCity.setText(user.city)
            etDescription.setText(user.description)
        }
    }
}
