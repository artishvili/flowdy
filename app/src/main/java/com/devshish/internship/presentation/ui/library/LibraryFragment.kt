package com.devshish.internship.presentation.ui.library

import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentLibraryBinding
import com.devshish.internship.databinding.ItemPlaylistBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class LibraryFragment : Fragment(R.layout.fragment_library) {

    private val binding by viewBinding(FragmentLibraryBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            layoutSavedSongs.setData(
                playlistCover = R.drawable.liked,
                playlistTitle = R.string.library_saved_lyrics,
                playlistItemCount = R.string.library_songs_count
            ) {
                val action = LibraryFragmentDirections.actionLibraryFragmentToLikedSongsFragment()
                findNavController().navigate(action)
            }

            layoutLocalSongs.setData(
                playlistCover = R.drawable.ic_folder,
                playlistTitle = R.string.library_local_songs,
                playlistItemCount = R.string.library_local_songs_count
            ) {
                val action = LibraryFragmentDirections.actionLibraryFragmentToLocalSongsFragment()
                findNavController().navigate(action)
            }

            layoutLocalAlbums.setData(
                playlistCover = R.drawable.ic_folder,
                playlistTitle = R.string.library_local_albums,
                playlistItemCount = R.string.library_local_albums_count
            ) {
                val action = LibraryFragmentDirections.actionLibraryFragmentToLocalAlbumsFragment()
                findNavController().navigate(action)
            }
        }
    }

    private fun ItemPlaylistBinding.setData(
        @DrawableRes playlistCover: Int,
        @StringRes playlistTitle: Int,
        @StringRes playlistItemCount: Int,
        navigateToPlaylist: () -> Unit
    ) {
        Glide.with(this@LibraryFragment)
            .load(playlistCover)
            .placeholder(playlistCover)
            .into(ivPlaylistCover)
        tvPlaylistTitle.text = getString(playlistTitle)
        tvPlaylistItemCount.text = getString(playlistItemCount)
        root.setOnClickListener { navigateToPlaylist() }
    }
}
