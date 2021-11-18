package com.devshish.internship.presentation.ui.home.tracks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.devshish.internship.databinding.ItemTrackBinding
import com.devshish.internship.domain.model.Track

class TrackItemAdapter : ListAdapter<Track, TrackViewHolder>(TrackItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val binding = ItemTrackBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TrackViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val curTrack = getItem(position)
        holder.bind(curTrack, position)
    }
}