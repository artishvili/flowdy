package com.devshish.internship.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentLibraryBinding

class LibraryFragment : Fragment(R.layout.fragment_library) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentLibraryBinding.bind(view)

        binding.clPlaylist.setOnClickListener {
            val action = LibraryFragmentDirections.actionLibraryFragmentToLikedSongsFragment()
            findNavController().navigate(action)
        }
    }
}
