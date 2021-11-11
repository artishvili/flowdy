package com.devshish.internship.presentation.ui.lyrics

import androidx.recyclerview.widget.DiffUtil
import com.devshish.internship.domain.model.Lyrics

class LyricsItemCallback : DiffUtil.ItemCallback<Lyrics>() {

    override fun areItemsTheSame(oldItem: Lyrics, newItem: Lyrics): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Lyrics, newItem: Lyrics): Boolean =
        oldItem == newItem
}
