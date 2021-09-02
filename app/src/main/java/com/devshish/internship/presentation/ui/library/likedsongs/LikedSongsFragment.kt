package com.devshish.internship.presentation.ui.library.likedsongs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentLikedSongsBinding
import com.devshish.internship.presentation.ui.library.ItemSongAdapter
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LikedSongsFragment : Fragment(R.layout.fragment_liked_songs) {

    private val binding by viewBinding(FragmentLikedSongsBinding::bind)
    private val viewModel: LikedSongsViewModel by viewModel()

    private val itemSongAdapter = ItemSongAdapter {
        viewModel.onSongClick(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvSongs.apply {
            adapter = itemSongAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.likedSongs.observe(viewLifecycleOwner) {
            itemSongAdapter.submitList(it)
        }
    }
}
