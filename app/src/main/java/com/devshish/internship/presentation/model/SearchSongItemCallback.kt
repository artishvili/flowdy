package com.devshish.internship.presentation.model

import androidx.recyclerview.widget.DiffUtil
import com.devshish.internship.domain.model.searchDTO.HitDTO

class SearchSongItemCallback : DiffUtil.ItemCallback<HitDTO>() {

    override fun areItemsTheSame(oldItem: HitDTO, newItem: HitDTO): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: HitDTO, newItem: HitDTO): Boolean =
        oldItem == newItem
}