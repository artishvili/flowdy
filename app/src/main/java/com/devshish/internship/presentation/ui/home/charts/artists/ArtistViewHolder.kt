package com.devshish.internship.presentation.ui.home.charts.artists

import androidx.recyclerview.widget.RecyclerView
import com.devshish.internship.databinding.ItemArtistBinding
import com.devshish.internship.domain.model.Artist

class ArtistViewHolder(
    private val binding: ItemArtistBinding,
    private val onClick: (Artist) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(artist: Artist, position: Int) {
        with(binding) {
            root.setOnClickListener { onClick(artist) }

            tvPosition.text = position.inc().toString()
            tvArtist.text = artist.name
            tvListeners.text = artist.listeners
            tvPlayCount.text = artist.playCount
        }
    }
}
