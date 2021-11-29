package com.devshish.internship.presentation.ui.albums.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentAlbumDetailsBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

@Deprecated("")
class AlbumDetailsFragment : Fragment(R.layout.fragment_album_details) {

    private val binding by viewBinding(FragmentAlbumDetailsBinding::bind)
    private val viewModel: AlbumDetailsViewModel by viewModel()
    private val itemAlbumSongsAdapter = ItemAlbumSongsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this)
            .load(R.color.purple_200)
            .placeholder(R.color.purple_200)
            .into(binding.ivAlbumCover)

        binding.apply {
            tvAlbumTitle.text = getString(R.string.library_details_album_title)
            tvArtist.text = getString(R.string.library_details_album_artist)
            rvAlbumsSongs.apply {
                adapter = itemAlbumSongsAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }

        viewModel.albumSongs.observe(viewLifecycleOwner) {
            itemAlbumSongsAdapter.submitList(it)
        }
    }
}
