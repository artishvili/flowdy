package com.devshish.internship.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.devshish.internship.databinding.ItemSongBinding
import com.devshish.internship.model.Song
import com.devshish.internship.utils.DiffCallback
import com.devshish.internship.utils.SongViewHolder

class ItemSongAdapter(
    private val listener: (Song) -> Unit
) : ListAdapter<Song, SongViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = ItemSongBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SongViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val curSong = getItem(position)
        holder.bind(curSong)
    }
}
