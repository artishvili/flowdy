package com.devshish.internship.presentation.ui.albums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.devshish.internship.databinding.ItemAlbumBinding
import com.devshish.internship.domain.model.Album
import com.devshish.internship.presentation.model.AlbumItemCallback

class ItemAlbumAdapter(
    private val onClick: (Album) -> Unit
) : ListAdapter<Album, AlbumViewHolder>(AlbumItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val binding = ItemAlbumBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AlbumViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val curAlbum = getItem(position)
        holder.bind(curAlbum)
    }
}
