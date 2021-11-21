package com.devshish.internship.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentHomeBinding
import com.devshish.internship.presentation.ui.home.charts.artists.ArtistItemAdapter
import com.devshish.internship.presentation.ui.home.charts.tracks.TrackItemAdapter
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val artistItemAdapter = ArtistItemAdapter(requireContext())
        val trackItemAdapter = TrackItemAdapter(requireContext())
        val concatAdapter = ConcatAdapter(artistItemAdapter, trackItemAdapter)

        binding.layoutCharts.rvItems.apply {
            adapter = concatAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        with(viewModel) {
            topArtists.observe(viewLifecycleOwner) { artists ->
                artistItemAdapter.addHeaderAndSubmitList(artists)
            }

            topTracks.observe(viewLifecycleOwner) { tracks ->
                trackItemAdapter.addHeaderAndSubmitList(tracks)
            }
        }
    }
}

