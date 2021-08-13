package com.devshish.internship.presentation.ui.likedsongs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentLikedSongsBinding
import com.devshish.internship.presentation.LikedSongsRepository
import com.devshish.internship.presentation.SongsRepository

class LikedSongsFragment : Fragment(R.layout.fragment_liked_songs) {

    //private val viewModel: LikedSongsViewModel by viewModels()
    private lateinit var viewModel: LikedSongsViewModel
    private lateinit var factory: LikedSongsViewModelFactory

    private val itemSongAdapter = ItemSongAdapter {
        viewModel.songClicked(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentLikedSongsBinding.bind(view)

        val repository = SongsRepository()
        factory = LikedSongsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(LikedSongsViewModel::class.java)


        binding.rvSongs.apply {
            adapter = itemSongAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.getLikedSongs().observe(viewLifecycleOwner) {
            itemSongAdapter.submitList(it)
        }
    }
}
