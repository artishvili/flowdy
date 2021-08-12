package com.devshish.internship.presentation.ui.likedsongs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentLikedSongsBinding

class LikedSongsFragment : Fragment(R.layout.fragment_liked_songs) {

    private val viewModel: LikedSongsViewModel by viewModels()

    private val itemSongAdapter = ItemSongAdapter {
        viewModel.songClicked(it)
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
