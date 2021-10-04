package com.devshish.internship.presentation.ui.main.albums.local

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentLocalAlbumsBinding
import com.devshish.internship.presentation.ui.main.albums.ItemAlbumAdapter
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LocalAlbumsFragment : Fragment(R.layout.fragment_local_albums) {

    private val binding by viewBinding(FragmentLocalAlbumsBinding::bind)
    private val viewModel: LocalAlbumsViewModel by viewModel()
    private val itemAlbumAdapter = ItemAlbumAdapter {
        val action = LocalAlbumsFragmentDirections.actionLocalAlbumsFragmentToAlbumDetailsFragment()
        findNavController().navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.layoutLocalAlbums.rvItems.apply {
            adapter = itemAlbumAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.localAlbums.observe(viewLifecycleOwner) {
            itemAlbumAdapter.submitList(it)
        }
    }
}
