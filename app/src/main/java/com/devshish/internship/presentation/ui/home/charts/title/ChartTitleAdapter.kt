package com.devshish.internship.presentation.ui.home.charts.title

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devshish.internship.databinding.ItemListHeaderBinding

class ChartTitleAdapter(
    private val title: String
) : RecyclerView.Adapter<TitleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
        val binding = ItemListHeaderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TitleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {
        holder.bind(title)
    }

    override fun getItemCount(): Int = 1
}
