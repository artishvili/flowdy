package com.devshish.internship.presentation.ui.lyrics

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentLyricsBinding
import com.google.android.material.snackbar.Snackbar
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class LyricsFragment : Fragment(R.layout.fragment_lyrics) {

    private val binding by viewBinding(FragmentLyricsBinding::bind)
    private val args: LyricsFragmentArgs by navArgs()
    private val viewModel: LyricsViewModel by viewModel { parametersOf(args.searchSong) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            ivSave.setOnClickListener {
                viewModel.onLikeButtonClick()
            }
        }

        with(viewModel) {
            lyrics.observe(viewLifecycleOwner) { lyrics ->
                binding.tvLyrics.text = lyrics
            }

            isProgressLoading.observe(viewLifecycleOwner) { isLoading ->
                binding.progressIndicator.isVisible = isLoading
            }

            isLyricsSaved.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let {
                    Snackbar.make(requireView(), R.string.lyrics_saved, Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.bottomNavView)
                        .show()
                }
            }
        }
    }
}
