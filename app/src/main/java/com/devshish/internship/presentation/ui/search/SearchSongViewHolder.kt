package com.devshish.internship.presentation.ui.search

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devshish.internship.databinding.ItemSearchSongBinding
import com.devshish.internship.domain.model.searchDTO.HitDTO

class SearchSongViewHolder(
    private val binding: ItemSearchSongBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(hitDTO: HitDTO) {
        with(binding) {
            tvSongName.text = hitDTO.result.title
            tvArtist.text = hitDTO.result.primaryArtist.name
            Glide.with(root)
                .load(hitDTO.result.headerImageUrl)
                .into(ivSongCover)
        }
    }
}
