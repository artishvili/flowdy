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

class LyricsFragment : Fragment(R.layout.fragment_lyrics) {

    private val binding by viewBinding(FragmentLyricsBinding::bind)
    private val viewModel: LyricsViewModel by viewModel()
    private val args: LyricsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            args.lyrics?.let {
                tvLyrics.text = it.content
            }

            ivSave.setOnClickListener {
                viewModel.onLikeButtonClick()
                Snackbar.make(
                    requireView(),
                    "Lyrics successfully saved.",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        with(viewModel) {
            args.searchSong?.let {
                getLyrics(it)
            }

            getLyricsEvent.observe(viewLifecycleOwner) { lyrics ->
                binding.tvLyrics.text = lyrics.content
            }

            isProgressLoading.observe(viewLifecycleOwner) { isLoading ->
                binding.progressIndicator.isVisible = isLoading
            }

            isLyricsSaved.observe(viewLifecycleOwner) { isSaved ->
                binding.ivSave.setImageResource(
                    if (isSaved) R.drawable.ic_favorite else R.drawable.ic_favorite_outlined
                )
            }
        }
    }
}
