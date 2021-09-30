package com.devshish.internship.presentation.model

import androidx.recyclerview.widget.DiffUtil
import com.devshish.internship.domain.model.SearchSong

class SearchSongItemCallback : DiffUtil.ItemCallback<SearchSong>() {

    override fun areItemsTheSame(oldItem: SearchSong, newItem: SearchSong): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: SearchSong, newItem: SearchSong): Boolean =
        oldItem == newItem
}
