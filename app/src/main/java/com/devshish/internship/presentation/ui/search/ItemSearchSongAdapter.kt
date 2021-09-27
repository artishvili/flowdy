package com.devshish.internship.presentation.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.devshish.internship.databinding.ItemSearchSongBinding
import com.devshish.internship.domain.model.searchDTO.HitDTO
import com.devshish.internship.presentation.model.SearchSongItemCallback

class ItemSearchSongAdapter :
    ListAdapter<HitDTO, SearchSongViewHolder>(SearchSongItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchSongViewHolder {
        val binding = ItemSearchSongBinding.inflate(
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
