package com.devshish.internship.presentation.ui.library

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devshish.internship.R
import com.devshish.internship.databinding.ItemSongBinding
import com.devshish.internship.domain.model.Song
import java.util.concurrent.TimeUnit

class SongViewHolder(
    private val binding: ItemSongBinding,
    private val onClick: (Song) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(song: Song) {
        binding.apply {
            root.setOnClickListener {
                onClick(song)
            }

            tvSongName.text = song.title
            tvArtist.text = song.artist
            tvDuration.text = String.format(
                    "%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(song.duration),
                    TimeUnit.MILLISECONDS.toSeconds(song.duration) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(song.duration)
                    )
                )

            Glide.with(ivSongCover)
                .load(song.imageUrl)
                .placeholder(R.drawable.liked)
                .into(ivSongCover)
        }
    }
}
