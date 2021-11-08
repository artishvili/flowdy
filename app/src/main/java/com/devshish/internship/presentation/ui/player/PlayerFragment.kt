package com.devshish.internship.presentation.ui.player

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentPlayerBinding
import com.devshish.internship.domain.model.Song
import com.devshish.internship.presentation.ui.utils.convertMillisToTime
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class PlayerFragment : Fragment(R.layout.fragment_player) {

    private val binding by viewBinding(FragmentPlayerBinding::bind)
    private val viewModel: PlayerViewModel by activityViewModels()

    private val seekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            viewModel.onProgressChanged(progress)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {}

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            seekBar?.let {
                viewModel.seekTo(it.progress.toLong())
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            sPlayer.setOnSeekBarChangeListener(seekBarChangeListener)

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
                binding.sPlayer.progress = position
            }
        }
    }

    private fun setSong(song: Song) {
        binding.apply {
            tvSong.text = song.title
            tvArtist.text = song.artist
            tvDurationEnd.text = convertMillisToTime(song.duration)
            sPlayer.max = song.duration

            Glide.with(this@PlayerFragment)
                .load(song.imageUri)
                .placeholder(R.color.black)
                .into(ivSongCover)
        }
    }
}
