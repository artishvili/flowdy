package com.devshish.internship.presentation.ui.songs.local

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentLocalSongsBinding
import com.devshish.internship.presentation.ui.songs.ItemSongAdapter
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LocalSongsFragment : Fragment(R.layout.fragment_local_songs) {

    private val binding by viewBinding(FragmentLocalSongsBinding::bind)
    private val viewModel: LocalSongsViewModel by viewModel()
    private val itemSongAdapter = ItemSongAdapter {
        viewModel.sendSong(it)

        val action = LocalSongsFragmentDirections.actionLocalSongsFragmentToPlayerFragment()
        findNavController().navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.layoutLocalSongs.rvItems.apply {
            adapter = itemSongAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.localSongs.observe(viewLifecycleOwner) {
            itemSongAdapter.submitList(it)
        }
    }
}
