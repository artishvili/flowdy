package com.devshish.internship.presentation.ui.library

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentLibraryBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class LibraryFragment : Fragment(R.layout.fragment_library) {

    private val binding by viewBinding(FragmentLibraryBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.clPlaylist.setOnClickListener {
            val action = LibraryFragmentDirections.actionLibraryFragmentToLikedSongsFragment()
            findNavController().navigate(action)
        }
    }
}
