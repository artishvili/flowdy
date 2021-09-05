package com.devshish.internship.presentation.ui.albums.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.devshish.internship.databinding.ItemAlbumSongBinding
import com.devshish.internship.domain.model.Song
import com.devshish.internship.presentation.model.SongItemCallback

class ItemAlbumSongsAdapter : ListAdapter<Song, AlbumDetailsViewHolder>(SongItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumDetailsViewHolder {
        val binding = ItemAlbumSongBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AlbumDetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumDetailsViewHolder, position: Int) {
        val curAlbum = getItem(position)
        holder.bind(curAlbum)
    }
}
