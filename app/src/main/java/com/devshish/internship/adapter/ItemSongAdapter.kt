package com.devshish.internship.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devshish.internship.databinding.ItemSongBinding
import com.devshish.internship.model.Song

class ItemSongAdapter : RecyclerView.Adapter<ItemSongAdapter.SongViewHolder>() {

    private var songs = emptyList<Song>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = ItemSongBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val curSong = songs[position]
        holder.bind(curSong)
    }

    override fun getItemCount() = songs.size

    inner class SongViewHolder(
        private val binding: ItemSongBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(song: Song) {
            binding.apply {
                tvSongName.text = song.name
                tvArtist.text = song.artist
            }
        }
    }

    fun setData(newSongsList: List<Song>) {
        songs = newSongsList
        notifyDataSetChanged()
    }
}
