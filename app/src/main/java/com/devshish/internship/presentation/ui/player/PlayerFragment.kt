package com.devshish.internship.presentation.ui.player

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentPlayerBinding
import com.devshish.internship.domain.model.Song
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class PlayerFragment : Fragment(R.layout.fragment_player) {

    private val binding by viewBinding(FragmentPlayerBinding::bind)
    private val viewModel: PlayerViewModel by viewModel()

    private val seekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            binding.tvDurationStart.text = convertMillisToTime(progress.toLong())
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

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

            tvPlaylist.text = getString(R.string.library_favorites)

            ivPlay.setOnClickListener {
                viewModel.toggle()
                viewModel.getState()
            }
        }

        with(viewModel) {
            songToPlay.observe(viewLifecycleOwner) {
                setSong(it)
            }

            isPlaying.observe(viewLifecycleOwner) { playing ->
                binding.ivPlay.setImageResource(
                    if (playing) R.drawable.ic_pause_filled else R.drawable.ic_play_filled
                )
            }

            currentPosition.observe(viewLifecycleOwner) {
                binding.sPlayer.progress = it.toInt()
            }
        }
    }

    // TODO
    // Fragment knows about the existence of the domain package!!
    private fun setSong(song: Song) {
        binding.apply {
            tvSong.text = song.title
            tvArtist.text = song.artist
            tvDurationStart.text = convertMillisToTime(0)
            tvDurationEnd.text = convertMillisToTime(song.duration.toLong())
            sPlayer.max = song.duration

            Glide.with(this@PlayerFragment)
                .load(song.imageUri)
                .placeholder(R.color.purple_200)
                .into(ivSongCover)
        }
    }

    private fun convertMillisToTime(ms: Long): String {
        val dateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
        return dateFormat.format(ms)
    }
}
