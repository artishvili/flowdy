package com.devshish.internship.ui.library

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.devshish.internship.R
import com.devshish.internship.adapter.ItemSongAdapter
import com.devshish.internship.databinding.FragmentLikedSongsBinding

class LikedSongsFragment : Fragment(R.layout.fragment_liked_songs) {

//    private lateinit var viewModel: LibraryViewModel
    private val viewModel: LibraryViewModel by viewModels()

    private val itemSongAdapter = ItemSongAdapter {
        Toast.makeText(requireContext(),
            "Selected ${it.title} to play.", Toast.LENGTH_LONG).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentLikedSongsBinding.bind(view)

        binding.rvSongs.apply {
            adapter = itemSongAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.songs.observe(viewLifecycleOwner) {
            itemSongAdapter.submitList(it)
        }
    }
}
