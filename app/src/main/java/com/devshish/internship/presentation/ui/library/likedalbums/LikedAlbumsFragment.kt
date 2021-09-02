package com.devshish.internship.presentation.ui.library.likedalbums

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentLikedAlbumsBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LikedAlbumsFragment : Fragment(R.layout.fragment_liked_albums) {

    private val binding by viewBinding(FragmentLikedAlbumsBinding::bind)
    private val viewModel: AlbumsViewModel by viewModel()
    private val itemAlbumAdapter = ItemAlbumAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvAlbums.apply {
            adapter = itemAlbumAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.albums.observe(viewLifecycleOwner) {
            itemAlbumAdapter.submitList(it)
        }
    }
}
