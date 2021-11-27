package com.devshish.internship.presentation.ui.player

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentPlayerBinding
import com.devshish.internship.domain.model.Song
import com.devshish.internship.presentation.ui.utils.convertMillisToTime
import com.google.android.material.slider.Slider
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class PlayerFragment : Fragment(R.layout.fragment_player) {

    private val binding by viewBinding(FragmentPlayerBinding::bind)
    private val viewModel: PlayerViewModel by activityViewModels()

    private val sliderChangeListener = Slider.OnChangeListener { _, value, _ ->
        viewModel.onProgressChanged(value.toInt())
    }

    private val sliderTouchListener = object : Slider.OnSliderTouchListener {
        override fun onStartTrackingTouch(slider: Slider) {}

        override fun onStopTrackingTouch(slider: Slider) {
            viewModel.seekTo(slider.value.toLong())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        (activity as AppCompatActivity).supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.ic_arrow_down)
            setDisplayHomeAsUpEnabled(true)
        }

        with(binding) {
            slider.apply {
                addOnChangeListener(sliderChangeListener)
                addOnSliderTouchListener(sliderTouchListener)
            }

            ivPlay.setOnClickListener { viewModel.toggle() }
        }

        with(viewModel) {
            songToPlay.observe(viewLifecycleOwner) { song ->
                setSong(song)
            }

            isPlaying.observe(viewLifecycleOwner) { playing ->
                binding.ivPlay.setImageResource(
                    if (playing) R.drawable.ic_pause_filled else R.drawable.ic_play_filled
                )
            }

            playbackPosition.observe(viewLifecycleOwner) { progress ->
                binding.tvDurationStart.text = progress
            }

            currentPosition.observe(viewLifecycleOwner) { position ->
                binding.slider.value = position.toFloat()
            }

            navigateToLyricsEvent.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { song ->
                    val action = PlayerFragmentDirections.actionPlayerFragmentToLyricsFragment(song)
                    findNavController().navigate(action)
                }
            }
        }
    }

    private fun setSong(song: Song) {
        binding.apply {
            tvSong.text = song.title
            tvArtist.text = song.artist
            tvDurationEnd.text = convertMillisToTime(song.duration)
            slider.valueFrom = 0f
            slider.valueTo = song.duration.toFloat()

            Glide.with(this@PlayerFragment)
                .load(song.imageUri)
                .placeholder(R.color.black)
                .into(ivSongCover)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        viewModel.isLyricsButtonVisible.observe(viewLifecycleOwner) { isVisible ->
            when (isVisible) {
                true -> {
                    menu.clear()
                    inflater.inflate(R.menu.player_menu, menu)
                }
                else -> super.onCreateOptionsMenu(menu, inflater)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.actionOpenLyrics -> {
                viewModel.onLyricsClick()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
