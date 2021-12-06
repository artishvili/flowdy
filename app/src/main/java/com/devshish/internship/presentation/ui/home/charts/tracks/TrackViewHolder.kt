package com.devshish.internship.presentation.ui.home.charts.tracks

import androidx.recyclerview.widget.RecyclerView
import com.devshish.internship.R
import com.devshish.internship.databinding.ItemTrackBinding
import com.devshish.internship.domain.model.Track

class TrackViewHolder(
    private val binding: ItemTrackBinding,
    private val onClick: (Track) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(track: Track, position: Int) {
        with(binding) {
            root.setOnClickListener { onClick(track) }

            tvPosition.text = position.inc().toString()
            tvArtist.apply {
                text = context.getString(R.string.home_chart_by_artist, track.artist)
            }
            tvTrack.text = track.name
            tvListeners.text = track.listeners
            tvPlayCount.text = track.playCount
        }
    }
}
