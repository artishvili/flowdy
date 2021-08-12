package com.devshish.internship.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devshish.internship.R
import com.devshish.internship.databinding.ItemSongBinding
import com.devshish.internship.model.Song

class ItemSongAdapter(
    private val listener: (Song) -> Unit
) : ListAdapter<Song, ItemSongAdapter.SongViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = ItemSongBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val curSong = getItem(position)
        holder.bind(curSong)
    }

    inner class SongViewHolder(
        private val binding: ItemSongBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val song = getItem(position)
                    listener(song)
                }
            }
        }

        fun bind(song: Song) {
            binding.apply {
                tvSongName.text = song.title
                tvArtist.text = song.artist

                val minutes = song.duration / 60
                val seconds = song.duration % 60
                val duration = String.format("%02d:%02d", minutes, seconds)
                tvDuration.text = duration

                Glide.with(ivSongCover)
                    .load(R.drawable.liked)
                    .into(ivSongCover)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Song>() {

        override fun areItemsTheSame(oldItem: Song, newItem: Song) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Song, newItem: Song) =
            oldItem == newItem
    }
}
