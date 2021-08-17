package com.devshish.internship.presentation.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentEditProfileBinding

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {

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
    }
}
