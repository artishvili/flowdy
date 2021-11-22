package com.devshish.internship.presentation.ui.home.charts.title

import androidx.recyclerview.widget.RecyclerView
import com.devshish.internship.databinding.ItemListHeaderBinding

class TitleViewHolder(
    private val binding: ItemListHeaderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(title: String) {
        binding.tvHeaderTitle.text = title
    }
}
