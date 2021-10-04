package com.devshish.internship.presentation.ui.albums.liked

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentLikedAlbumsBinding
import com.devshish.internship.presentation.ui.albums.ItemAlbumAdapter
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LikedAlbumsFragment : Fragment(R.layout.fragment_liked_albums) {

    private val binding by viewBinding(FragmentLikedAlbumsBinding::bind)
    private val viewModel: LikedAlbumsViewModel by viewModel()
    private val itemAlbumAdapter = ItemAlbumAdapter {
        val action = LikedAlbumsFragmentDirections.actionLikedAlbumsFragmentToAlbumDetailsFragment()
        findNavController().navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.layoutLikedAlbums.rvItems.apply {
            adapter = itemAlbumAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.albums.observe(viewLifecycleOwner) {
            itemAlbumAdapter.submitList(it)
        }
    }
}
