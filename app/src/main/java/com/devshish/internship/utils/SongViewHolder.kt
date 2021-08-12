package com.devshish.internship.utils

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devshish.internship.R
import com.devshish.internship.databinding.ItemSongBinding
import com.devshish.internship.model.Song

class SongViewHolder(
    private val binding: ItemSongBinding,
    private val listener: (Song) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(song: Song) {
        binding.apply {
            root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener(song)
                }
            }

            tvSongName.text = song.title
            tvArtist.text = song.artist

            val minutes = song.duration / 60
            val seconds = song.duration % 60
            val duration = String.format("%02d:%02d", minutes, seconds)
            tvDuration.text = duration

            Glide.with(ivSongCover)
                .load(song.imageUrl)
                .placeholder(R.drawable.liked)
                .into(ivSongCover)
        }
    }
}