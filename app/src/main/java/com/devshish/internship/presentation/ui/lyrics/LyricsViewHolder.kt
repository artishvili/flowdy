package com.devshish.internship.presentation.ui.lyrics

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devshish.internship.R
import com.devshish.internship.databinding.ItemLyricsBinding
import com.devshish.internship.domain.model.Lyrics

class LyricsViewHolder(
    private val binding: ItemLyricsBinding,
    private val onClick: (Lyrics) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(lyrics: Lyrics) {
        binding.apply {
            root.setOnClickListener {
                onClick(lyrics)
            }

            tvSongName.text = lyrics.title
            tvArtist.text = lyrics.artist

            Glide.with(ivSongCover)
                .load(lyrics.imageUri)
                .placeholder(R.drawable.liked)
                .into(ivSongCover)
        }
    }
}
