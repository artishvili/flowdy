package com.devshish.internship.presentation.ui.home.charts.artists

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devshish.internship.R
import com.devshish.internship.databinding.ItemArtistBinding
import com.devshish.internship.databinding.ItemListHeaderBinding
import com.devshish.internship.domain.model.Artist
import com.devshish.internship.presentation.model.ChartItem
import com.devshish.internship.presentation.model.ChartItemCallback
import com.devshish.internship.presentation.ui.home.charts.TitleViewHolder

class ArtistItemAdapter(
    private val context: Context
) : ListAdapter<ChartItem, RecyclerView.ViewHolder>(ChartItemCallback()) {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEM = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ChartItem.Header -> TYPE_HEADER
            is ChartItem.ArtistItem -> TYPE_ITEM
            else -> Int.MIN_VALUE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                val headerBinding = ItemListHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                TitleViewHolder(headerBinding)
            }
            TYPE_ITEM -> {
                val binding = ItemArtistBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ArtistViewHolder(binding)
            }
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ArtistViewHolder -> {
                val dataItem = getItem(position) as ChartItem.ArtistItem
                holder.bind(dataItem.artist, position)
            }
            is TitleViewHolder -> holder.bind(context.getString(R.string.home_chart_artists))
        }
    }

    fun addHeaderAndSubmitList(list: List<Artist>?) {
        val items = when (list) {
            null -> listOf(ChartItem.Header)
            else -> listOf(ChartItem.Header) + list.map { ChartItem.ArtistItem(it) }
        }
        submitList(items)
    }
}
