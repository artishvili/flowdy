package com.devshish.internship.presentation.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentEditProfileBinding

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {

    private val viewModel: EditProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentEditProfileBinding.bind(view)

        Glide.with(this)
            .load("https://cdn.vox-cdn.com/uploads/chorus_asset/file/22661965/img19.jpg")
            .placeholder(R.drawable.liked)
            .into(binding.ivBackgroundPicture)

        Glide.with(this)
            .load("https://api.time.com/wp-content/uploads/2014/09/macaca_nigra_self-portrait_rotated_and_cropped.jpg")
            .placeholder(R.drawable.liked)
            .into(binding.ivProfilePicture)

        binding.apply {
            btnSaveChanges.setOnClickListener {
                viewModel.onSaveButtonClick(
                    nickname = etNickname.text.toString(),
                    country = etCountry.text.toString(),
                    city = etCity.text.toString(),
                    description = etDescription.text.toString()
                )
                viewModel.navigateBack.observe(viewLifecycleOwner) {
                    val action = EditProfileFragmentDirections.actionEditProfileFragmentToProfileFragment()
                    findNavController().navigate(action)
                }
            }
        }
    }
}
