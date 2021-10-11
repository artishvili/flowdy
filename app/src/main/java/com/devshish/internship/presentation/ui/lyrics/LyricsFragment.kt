package com.devshish.internship.presentation.ui.lyrics

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentLyricsBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LyricsFragment : Fragment(R.layout.fragment_lyrics) {

    private val binding by viewBinding(FragmentLyricsBinding::bind)
    private val viewModel: LyricsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {
            getLyricsEvent.observe(viewLifecycleOwner) {
                binding.tvLyrics.text = it
            }
        }
    }
}
