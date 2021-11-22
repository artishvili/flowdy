package com.devshish.internship.presentation.ui.home.charts.artists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.devshish.internship.databinding.ItemArtistBinding
import com.devshish.internship.domain.model.Artist
import com.devshish.internship.presentation.model.ArtistItemCallback

class ArtistItemAdapter : ListAdapter<Artist, ArtistViewHolder>(ArtistItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val binding = ItemArtistBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArtistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        val curArtist = getItem(position)
        holder.bind(curArtist, position)
    }
}
