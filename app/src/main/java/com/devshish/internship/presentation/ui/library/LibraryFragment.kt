package com.devshish.internship.presentation.ui.library

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentLibraryBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class LibraryFragment : Fragment(R.layout.fragment_library) {

    private val binding by viewBinding(FragmentLibraryBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            layoutLikedSongs.apply {
                Glide.with(root)
                    .load(R.drawable.liked)
                    .placeholder(R.drawable.liked)
                    .into(ivPlaylistCover)

                tvPlaylistTitle.text = getString(R.string.library_favorites)
                tvPlaylistItemCount.text = getString(R.string.library_songs_count)

                root.setOnClickListener {
                    val action = LibraryFragmentDirections
                        .actionLibraryFragmentToLikedSongsFragment()
                    findNavController().navigate(action)
                }
            }

            layoutLikedAlbums.apply {
                Glide.with(root)
                    .load(R.drawable.album)
                    .placeholder(R.drawable.album)
                    .into(ivPlaylistCover)

                tvPlaylistTitle.text = getString(R.string.library_albums)
                tvPlaylistItemCount.text = getString(R.string.library_albums_count)

                root.setOnClickListener {
                    val action = LibraryFragmentDirections
                        .actionLibraryFragmentToLikedAlbumsFragment()
                    findNavController().navigate(action)
                }
            }
        }
    }
}
