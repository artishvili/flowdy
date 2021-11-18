package com.devshish.internship.presentation.ui.home.tracks

import androidx.recyclerview.widget.RecyclerView
import com.devshish.internship.databinding.ItemTrackBinding
import com.devshish.internship.domain.model.Track

class TrackViewHolder(
    private val binding: ItemTrackBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(track: Track, position: Int) {
        with(binding) {
            tvPosition.text = position.plus(1).toString()
            tvArtist.text = track.artist
            tvTrack.text = track.name
            tvListeners.text = track.listeners
            tvPlayCount.text = track.playCount
        }
    }
}
