package com.devshish.internship.presentation.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.devshish.internship.databinding.ItemSongBinding
import com.devshish.internship.domain.model.SearchSong
import com.devshish.internship.presentation.model.SearchSongItemCallback

class ItemSearchSongAdapter :
    ListAdapter<SearchSong, SearchSongViewHolder>(SearchSongItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchSongViewHolder {
        val binding = ItemSongBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchSongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchSongViewHolder, position: Int) {
        val currentHit = getItem(position)
        holder.bind(currentHit)
    }
}
