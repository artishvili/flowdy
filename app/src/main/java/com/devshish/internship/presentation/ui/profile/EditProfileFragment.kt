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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {

    private lateinit var binding: FragmentEditProfileBinding

    private val viewModel: EditProfileViewModel by lazy {
        val factory = EditProfileViewModelFactory(ProfileRepository)
        ViewModelProvider(this, factory).get(EditProfileViewModel::class.java)
    }

    private val getProfileImage: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri: Uri? ->
            viewModel.onProfileImagePick(imageUri)
        }

    private val getBackgroundImage: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri: Uri? ->
            viewModel.onBackgroundImagePick(imageUri)
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
                    launch {
                        profileImageFlow.collect { uri ->
                            Glide.with(this@EditProfileFragment)
                                .load(uri)
                                .placeholder(R.drawable.ic_profile)
                                .into(binding.ivProfilePicture)
                        }
                    }
                    launch {
                        backgroundImageFlow.collect { uri ->
                            Glide.with(this@EditProfileFragment)
                                .load(uri)
                                .placeholder(R.drawable.ic_profile)
                                .into(binding.ivBackgroundPicture)
                        }
                    }
                    launch { userFlow.collect { user -> showUser(user) } }
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
            Glide.with(binding.ivProfilePicture)
                .load(user.photo)
                .placeholder(R.drawable.ic_profile)
                .into(ivProfilePicture)
            Glide.with(binding.ivBackgroundPicture)
                .load(user.background)
                .placeholder(R.drawable.ic_profile)
                .into(ivBackgroundPicture)
        }
    }
}
