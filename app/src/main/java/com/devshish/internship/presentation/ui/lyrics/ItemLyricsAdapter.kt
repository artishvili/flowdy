package com.devshish.internship.presentation.ui.lyrics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.devshish.internship.databinding.ItemLyricsBinding
import com.devshish.internship.domain.model.Lyrics

class ItemLyricsAdapter : ListAdapter<Lyrics, LyricsViewHolder>(LyricsItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LyricsViewHolder {
        val binding = ItemLyricsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LyricsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LyricsViewHolder, position: Int) {
        val curLyrics = getItem(position)
        holder.bind(curLyrics)
    }
}
