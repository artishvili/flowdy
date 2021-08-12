package com.devshish.internship.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.devshish.internship.databinding.ItemSongBinding
import com.devshish.internship.model.Song
import com.devshish.internship.utils.SongItemCallback
import com.devshish.internship.utils.SongViewHolder

class ItemSongAdapter(
    private val onClick: (Song) -> Unit
) : ListAdapter<Song, SongViewHolder>(SongItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = ItemSongBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SongViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val curSong = getItem(position)
        holder.bind(curSong)
    }
}
