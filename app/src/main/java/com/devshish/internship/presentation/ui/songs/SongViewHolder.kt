package com.devshish.internship.presentation.ui.songs

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devshish.internship.R
import com.devshish.internship.databinding.ItemSongBinding
import com.devshish.internship.domain.model.Song
import com.devshish.internship.presentation.ui.utils.convertMillisToTime

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
//            tvDuration.text = convertMillisToTime(song.duration)

            Glide.with(ivSongCover)
                .load(song.imageUri)
                .placeholder(R.drawable.liked)
                .into(ivSongCover)
        }
    }
}
