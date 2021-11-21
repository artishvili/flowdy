package com.devshish.internship.presentation.ui.home.charts.tracks

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devshish.internship.R
import com.devshish.internship.databinding.ItemListHeaderBinding
import com.devshish.internship.databinding.ItemTrackBinding
import com.devshish.internship.domain.model.Track
import com.devshish.internship.presentation.model.ChartItem
import com.devshish.internship.presentation.model.ChartItemCallback
import com.devshish.internship.presentation.ui.home.charts.TitleViewHolder

class TrackItemAdapter(
    private val context: Context
) : ListAdapter<ChartItem, RecyclerView.ViewHolder>(ChartItemCallback()) {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEM = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ChartItem.Header -> TYPE_HEADER
            is ChartItem.TrackItem -> TYPE_ITEM
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
                val binding = ItemTrackBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                TrackViewHolder(binding)
            }
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TrackViewHolder -> {
                val dataItem = getItem(position) as ChartItem.TrackItem
                holder.bind(dataItem.track, position)
            }
            is TitleViewHolder -> holder.bind(context.getString(R.string.home_chart_tracks))
        }
    }

    fun addHeaderAndSubmitList(list: List<Track>?) {
        val items = when (list) {
            null -> listOf(ChartItem.Header)
            else -> listOf(ChartItem.Header) + list.map { ChartItem.TrackItem(it) }
        }
        submitList(items)
    }
}