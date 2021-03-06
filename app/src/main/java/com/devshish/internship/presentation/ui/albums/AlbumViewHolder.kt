package com.devshish.internship.presentation.ui.albums

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devshish.internship.R
import com.devshish.internship.databinding.ItemAlbumBinding
import com.devshish.internship.domain.model.Album

@Deprecated("")
class AlbumViewHolder(
    private val binding: ItemAlbumBinding,
    private val onClick: (Album) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(album: Album) {
        binding.apply {
            root.setOnClickListener {
                onClick(album)
            }

            tvAlbumTitle.text = album.title
            tvArtist.text = album.artist

            Glide.with(root)
                .load(album.cover)
                .placeholder(R.color.purple_200)
                .into(ivAlbumCover)
        }
    }
}
