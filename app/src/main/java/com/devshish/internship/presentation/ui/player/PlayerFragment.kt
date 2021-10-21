package com.devshish.internship.presentation.ui.player

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentPlayerBinding
import com.devshish.internship.domain.model.Song
import com.devshish.internship.presentation.ui.utils.convertMillisToTime
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerFragment : Fragment(R.layout.fragment_player) {

    private val binding by viewBinding(FragmentPlayerBinding::bind)
    private val viewModel: PlayerViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            tvPlaylist.text = getString(R.string.library_favorites)
            tvDurationStart.text = convertMillisToTime(0)

            ivPlay.setOnClickListener { viewModel.toggle() }
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
        }

        with(viewModel) {
            with(binding) {
                songToPlay.observe(viewLifecycleOwner) {
                    tvSong.text = it.title
                    tvArtist.text = it.artist
                    tvDurationEnd.text = convertMillisToTime(it.duration)

                    Glide.with(this@PlayerFragment)
                        .load(it.imageUri)
                        .placeholder(R.color.purple_200)
                        .into(ivSongCover)
                }
            }
        }
    }

    private fun setSong(song: Song) {
        binding.apply {
            tvSong.text = song.title
            tvArtist.text = song.artist
            tvDurationStart.text = convertMillisToTime(0)
            tvDurationEnd.text = convertMillisToTime(song.duration)

            Glide.with(this@PlayerFragment)
                .load(song.imageUri)
                .placeholder(R.color.purple_200)
                .into(ivSongCover)
        }
    }
}
