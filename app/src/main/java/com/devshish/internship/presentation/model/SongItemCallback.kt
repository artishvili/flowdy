package com.devshish.internship.presentation.model

import androidx.recyclerview.widget.DiffUtil
import com.devshish.internship.domain.models.Song

class SongItemCallback : DiffUtil.ItemCallback<Song>() {

    override fun areItemsTheSame(oldItem: Song, newItem: Song) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Song, newItem: Song) =
        oldItem == newItem
}