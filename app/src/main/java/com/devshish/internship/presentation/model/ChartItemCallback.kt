package com.devshish.internship.presentation.model

import androidx.recyclerview.widget.DiffUtil

class ChartItemCallback : DiffUtil.ItemCallback<ChartItem>() {

    override fun areItemsTheSame(oldItem: ChartItem, newItem: ChartItem): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: ChartItem, newItem: ChartItem): Boolean =
        oldItem == newItem
}
