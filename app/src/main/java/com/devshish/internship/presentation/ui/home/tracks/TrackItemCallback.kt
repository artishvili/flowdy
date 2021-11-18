package com.devshish.internship.presentation.ui.home.tracks

import androidx.recyclerview.widget.DiffUtil
import com.devshish.internship.domain.model.Track

class TrackItemCallback : DiffUtil.ItemCallback<Track>() {

    override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean =
        oldItem == newItem
}
