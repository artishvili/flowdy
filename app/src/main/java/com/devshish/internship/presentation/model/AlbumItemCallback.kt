package com.devshish.internship.presentation.model

import androidx.recyclerview.widget.DiffUtil
import com.devshish.internship.domain.model.Album

class AlbumItemCallback : DiffUtil.ItemCallback<Album>() {

    override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean =
        oldItem == newItem
}