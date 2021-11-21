package com.devshish.internship.presentation.ui.home.charts.artists

import androidx.recyclerview.widget.RecyclerView
import com.devshish.internship.databinding.ItemArtistBinding
import com.devshish.internship.domain.model.Artist

class ArtistViewHolder(
    private val binding: ItemArtistBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(artist: Artist, position: Int) {
        with(binding) {
            tvPosition.text = position.toString()
            tvArtist.text = artist.name
            tvListeners.text = artist.listeners
            tvPlayCount.text = artist.playCount
        }
    }
}
