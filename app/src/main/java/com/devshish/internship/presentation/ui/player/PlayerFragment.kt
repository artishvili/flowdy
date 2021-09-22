package com.devshish.internship.presentation.ui.player

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentPlayerBinding
import com.devshish.internship.presentation.ui.utils.convertMillisToTime
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class PlayerFragment : Fragment(R.layout.fragment_player) {

    private val binding by viewBinding(FragmentPlayerBinding::bind)
    private val viewModel: PlayerViewModel by viewModel()
    private val args: PlayerFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            tvPlaylist.text = getString(R.string.library_songs)
            tvSong.text = args.song.title
            tvArtist.text = args.song.artist
            tvDurationStart.text = convertMillisToTime(0)
            tvDurationEnd.text = convertMillisToTime(args.song.duration)
            seekBar.max = args.song.duration

            Glide.with(this@PlayerFragment)
                .load(args.song.imageUri)
                .placeholder(R.color.purple_200)
                .into(ivSongCover)

            ivPlay.setOnClickListener {
                viewModel.toggle(args.song)
            }

            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, position: Int, changed: Boolean) {
                    if (changed) {
                        tvDurationStart.text = convertMillisToTime(position)
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) = Unit

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    seekBar?.let {
                        viewModel.seekTo(it.progress)
                    }
                }
            })
        }

        with(viewModel) {
            viewModelScope.launch {
                delay(500)
                toggle(args.song)
            }

            isPlaying.observe(viewLifecycleOwner) {
                binding.ivPlay.setImageResource(
                    if (it) R.drawable.ic_pause_filled else
                        R.drawable.ic_play_filled
                )
            }

            currentPosition.observe(viewLifecycleOwner) {
                binding.seekBar.progress = it.toInt()
                binding.tvDurationStart.text = convertMillisToTime(it.toInt())
            }
        }
    }
}
