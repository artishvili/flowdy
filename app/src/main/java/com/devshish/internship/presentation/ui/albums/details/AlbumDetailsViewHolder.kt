package com.devshish.internship.presentation.ui.albums.details

import androidx.recyclerview.widget.RecyclerView
import com.devshish.internship.databinding.ItemAlbumSongBinding
import com.devshish.internship.domain.model.Song
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.*

class AlbumDetailsViewHolder(
    private val binding: ItemAlbumSongBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(song: Song) {
        binding.apply {
            tvSongPosition.text = adapterPosition.plus(1).toString()
            tvArtist.text = song.artist
            tvSongName.text = song.title

            val minutes = MILLISECONDS.toMinutes(song.duration.toLong())
            val seconds = MILLISECONDS.toSeconds(song.duration.toLong()) - MINUTES.toSeconds(minutes)
            tvDuration.text = String.format("%02d:%02d", minutes, seconds)
        }
    }
}