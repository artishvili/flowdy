package com.devshish.internship.presentation.ui.lyrics

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentLyricsBinding
import com.devshish.internship.domain.model.SearchSong
import com.devshish.internship.presentation.ui.utils.caseSmthWentWrong
import com.google.android.material.snackbar.Snackbar
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class LyricsFragment : Fragment(R.layout.fragment_lyrics) {

    private val binding by viewBinding(FragmentLyricsBinding::bind)
    private val args: LyricsFragmentArgs by navArgs()
    private val viewModel: LyricsViewModel by viewModel { parametersOf(args.searchSong) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topAppBar.apply {
            setupWithNavController(findNavController())

            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.actionSaveSong -> {
                        viewModel.onStoreSongClick()
                        true
                    }
                    R.id.actionDeleteSong -> {
                        viewModel.onDeleteSongClick()
                        true
                    }
                    else -> false
                }
            }
        }

        with(viewModel) {
            exception.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { messageRes ->
                    Snackbar.make(requireView(), messageRes, Snackbar.LENGTH_LONG)
                        .caseSmthWentWrong { getLyrics() }
                }
            }

            isLyricsStored.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { messageRes ->
                    Snackbar.make(requireView(), messageRes, Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.bottomNavView)
                        .show()
                }
            }

            uiState.observe(viewLifecycleOwner) { state ->
                Timber.d("UI STATE: ${state.javaClass.simpleName}")

                val storeSongItem = binding.topAppBar.menu.findItem(R.id.actionSaveSong)
                val deleteSongItem = binding.topAppBar.menu.findItem(R.id.actionDeleteSong)

                when (state) {
                    is LyricsViewModel.UIState.IsLoading -> {
                        setupLayout(state.song)
                        binding.progressIndicator.isVisible = true
                        storeSongItem.isVisible = false
                        deleteSongItem.isVisible = false
                    }
                    is LyricsViewModel.UIState.Loaded -> {
                        binding.progressIndicator.isVisible = false
                        binding.tvLyrics.text = state.lyrics
                        storeSongItem.isVisible = !state.isStored
                        deleteSongItem.isVisible = state.isStored
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setupLayout(song: SearchSong) {
        binding.apply {
            topAppBar.apply {
                title = song.title
                subtitle = song.artist
            }

            Glide.with(this@LyricsFragment)
                .load(song.imageUri)
                .placeholder(R.color.black)
                .into(ivSongCover)
        }
    }
}
