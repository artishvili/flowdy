package com.devshish.internship.presentation.model

import androidx.recyclerview.widget.DiffUtil
import com.devshish.internship.domain.model.Artist

class ArtistItemCallback : DiffUtil.ItemCallback<Artist>() {

    override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean =
        oldItem == newItem
}
