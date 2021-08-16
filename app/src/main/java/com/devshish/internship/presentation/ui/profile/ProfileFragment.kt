package com.devshish.internship.presentation.ui.profile

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.devshish.internship.R
import com.devshish.internship.data.repository.ProfileRepository
import com.devshish.internship.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by lazy {
        val repository = ProfileRepository()
        val factory = ProfileViewModelFactory(repository)
        ViewModelProvider(this, factory).get(ProfileViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentProfileBinding.bind(view)

        viewModel.user.observe(viewLifecycleOwner) { user ->
            binding.apply {
                Glide.with(this@ProfileFragment)
                    .load(user.photo)
                    .placeholder(R.drawable.ic_profile)
                    .into(ivProfilePicture)

                tvNickname.text = user.nickname
                tvDescription.checkAndSetText(user.description, R.string.profile_user_description)
                tvCountry.checkAndSetText(user.country, R.string.profile_user_country)
                tvCity.checkAndSetText(user.city, R.string.profile_user_city)
                tvBackground.checkAndSetText(user.background, R.string.profile_user_background)
            }
        }
    }

    private fun TextView.checkAndSetText(data: String?, resourceId: Int) {
        val checkedData = data ?: getString(R.string.data_not_chosen)
        text = getString(resourceId, checkedData)
    }
}
