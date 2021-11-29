package com.devshish.internship.presentation.ui.albums.details

import androidx.recyclerview.widget.RecyclerView
import com.devshish.internship.databinding.ItemAlbumSongBinding
import com.devshish.internship.domain.model.Song
import com.devshish.internship.presentation.ui.utils.convertMillisToTime

@Deprecated("")
class AlbumDetailsViewHolder(
    private val binding: ItemAlbumSongBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(song: Song) {
        binding.apply {
            tvSongPosition.text = adapterPosition.plus(1).toString()
            tvArtist.text = song.artist
            tvSongName.text = song.title
            tvDuration.text = convertMillisToTime(song.duration)
        }
    }
}
