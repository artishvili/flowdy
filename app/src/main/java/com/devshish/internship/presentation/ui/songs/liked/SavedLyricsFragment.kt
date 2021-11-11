package com.devshish.internship.presentation.ui.songs.liked

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentSavedLyricsBinding
import com.devshish.internship.presentation.ui.lyrics.ItemLyricsAdapter
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SavedLyricsFragment : Fragment(R.layout.fragment_saved_lyrics) {

    private val binding by viewBinding(FragmentSavedLyricsBinding::bind)
    private val viewModel: SavedLyricsViewModel by viewModel()

    private val itemLyricsAdapter = ItemLyricsAdapter { lyrics ->
        viewModel.onLyricsClick(lyrics)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.layoutLikedSongs.rvItems.apply {
            adapter = itemLyricsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        with(viewModel) {
            savedSongs.observe(viewLifecycleOwner) {
                itemLyricsAdapter.submitList(it)
            }

            navigationEvent.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { lyrics ->
                    val action = SavedLyricsFragmentDirections
                        .actionSavedLyricsFragmentToLyricsFragment(lyrics = lyrics)
                    findNavController().navigate(action)
                }
            }
        }
    }
}
