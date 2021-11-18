package com.devshish.internship.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentHomeBinding
import com.devshish.internship.presentation.ui.home.artist.ArtistItemAdapter
import com.devshish.internship.presentation.ui.home.tracks.TrackItemAdapter
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModel()

    private val artistItemAdapter = ArtistItemAdapter()
    private val trackItemAdapter = TrackItemAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            rvItems.apply {
                adapter = artistItemAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }

            rvArtists.apply {
                adapter = trackItemAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }

        with(viewModel) {
            topArtists.observe(viewLifecycleOwner) { artists ->
                artistItemAdapter.submitList(artists)
            }

            topTracks.observe(viewLifecycleOwner) { tracks ->
                trackItemAdapter.submitList(tracks)
            }
        }
    }
}

