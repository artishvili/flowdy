package com.devshish.internship.presentation.ui.main.search

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devshish.internship.databinding.ItemSongBinding
import com.devshish.internship.domain.model.SearchSong

class SearchSongViewHolder(
    private val binding: ItemSongBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(searchSong: SearchSong) {
        with(binding) {
            tvSongName.text = searchSong.title
            tvArtist.text = searchSong.artist
            Glide.with(root)
                .load(searchSong.imageUri)
                .into(ivSongCover)
        }
    }
}
