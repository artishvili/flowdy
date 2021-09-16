package com.devshish.internship.presentation.ui.player

import android.os.Bundle
import android.support.v4.media.session.PlaybackStateCompat
import android.view.View
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentPlayerBinding
import com.devshish.internship.domain.model.Song
import com.devshish.internship.presentation.ui.MainViewModel
import com.devshish.internship.presentation.ui.utils.Status
import com.devshish.internship.presentation.ui.utils.convertMillisToTime
import com.devshish.internship.presentation.ui.utils.isPlaying
import com.devshish.internship.presentation.ui.utils.toSong
import com.google.android.material.slider.Slider
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class PlayerFragment : Fragment(R.layout.fragment_player) {

    private val binding by viewBinding(FragmentPlayerBinding::bind)

    private val mainViewModel: MainViewModel by viewModel()
    private val playerViewModel: PlayerViewModel by viewModel()

    private val args: PlayerFragmentArgs by navArgs()

    private var curPlayingSong: Song? = null

    private var playbackState: PlaybackStateCompat? = null

    private var shouldUpdateSeekbar = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToObservers()
        mainViewModel.playOrToggleSong(args.song, true)

        with(binding) {
            sPlayer.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    if (p2) {
                        setCurrentPlayerTimeToTextView(p1.toLong())
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                    shouldUpdateSeekbar = false
                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                    p0?.let {
                        mainViewModel.seekTo(it.progress.toLong())
                        shouldUpdateSeekbar = true
                    }
                }
            })

            tvPlaylist.text = getString(R.string.library_songs)
            tvSong.text = args.song.title
            tvArtist.text = args.song.artist
            tvDurationStart.text = convertMillisToTime(0)
            tvDurationEnd.text = convertMillisToTime(args.song.duration)

            Glide.with(this@PlayerFragment)
                .load(args.song.imageUri)
                .placeholder(R.color.purple_200)
                .into(ivSongCover)

            ivPlay.setOnClickListener {
                mainViewModel.playOrToggleSong(args.song, true)
            }
        }
    }

    private fun subscribeToObservers() {
        mainViewModel.mediaItems.observe(viewLifecycleOwner) {
            it?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        result.data?.let { songs ->
                            if (curPlayingSong == null && songs.isNotEmpty()) {
                                curPlayingSong = songs[0]
                            }
                        }
                    }
                    else -> Unit
                }
            }
        }

        mainViewModel.curPlayingSong.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            curPlayingSong = it.toSong()

        }

        mainViewModel.playbackState.observe(viewLifecycleOwner) {
            playbackState = it
            binding.ivPlay.setImageResource(
                if (playbackState?.isPlaying == true) R.drawable.ic_pause_filled else
                    R.drawable.ic_play_filled
            )
            binding.sPlayer.progress = it?.position?.toInt() ?: 0
        }

        playerViewModel.curPlayerPosition.observe(viewLifecycleOwner) {
            if (shouldUpdateSeekbar) {
                binding.sPlayer.progress = it.toInt()
                setCurrentPlayerTimeToTextView(it)
            }
        }

        playerViewModel.curSongDuration.observe(viewLifecycleOwner) {
            binding.sPlayer.max = it.toInt()
        }
    }

    private fun setCurrentPlayerTimeToTextView(ms: Long) {
        val dateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
        binding.tvDurationStart.text = dateFormat.format(ms)
    }
}
