package com.devshish.internship.presentation.ui.player

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentPlayerBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerFragment : Fragment(R.layout.fragment_player) {

    private val binding by viewBinding(FragmentPlayerBinding::bind)
    private val viewModel: PlayerViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            ivPlay.setOnClickListener { viewModel.toggle() }
        }

        with(viewModel) {
            with(binding) {
                songToPlay.observe(viewLifecycleOwner) {
                    tvPlaylist.text = getString(R.string.library_favorites)
                    tvSong.text = it.title
                    tvArtist.text = it.artist

                    // TODO: IMPLEMENT REAL DESTINATION START AND END
                    tvDurationStart.text = "0:00"
                    tvDurationEnd.text = "3:40"

                    Glide.with(this@PlayerFragment)
                        .load(it.imageUri)
                        .placeholder(R.color.purple_200)
                        .into(ivSongCover)
                }
            }
        }
    }
}
