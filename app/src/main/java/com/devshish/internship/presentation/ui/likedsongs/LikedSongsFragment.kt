package com.devshish.internship.presentation.ui.likedsongs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentLikedSongsBinding
import com.devshish.internship.data.repository.SongsRepository

class LikedSongsFragment : Fragment(R.layout.fragment_liked_songs) {

    private val viewModel: LikedSongsViewModel by lazy {
        val repository = SongsRepository()
        val factory = LikedSongsViewModelFactory(repository)
        ViewModelProvider(this, factory).get(LikedSongsViewModel::class.java)
    }

    private val itemSongAdapter = ItemSongAdapter {
        viewModel.onSongClick(it)
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
