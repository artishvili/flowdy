package com.devshish.internship.presentation.ui.player

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentPlayerBinding
import com.devshish.internship.domain.model.Song
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerFragment : Fragment(R.layout.fragment_player) {

    private val binding by viewBinding(FragmentPlayerBinding::bind)
    private val viewModel: PlayerViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
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
        }
    }

    // TODO
    // Fragment knows about the existence of the domain package!!
    private fun setSong(song: Song) {
        binding.apply {
            tvSong.text = song.title
            tvArtist.text = song.artist

            // TODO: IMPLEMENT REAL DESTINATION START AND END
            tvDurationStart.text = "0:00"
            tvDurationEnd.text = "3:40"

            Glide.with(this@PlayerFragment)
                .load(song.imageUri)
                .placeholder(R.color.purple_200)
                .into(ivSongCover)
        }
    }
}
