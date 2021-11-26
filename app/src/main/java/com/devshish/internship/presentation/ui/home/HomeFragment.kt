package com.devshish.internship.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentHomeBinding
import com.devshish.internship.presentation.ui.home.charts.artists.ArtistItemAdapter
import com.devshish.internship.presentation.ui.home.charts.title.ChartTitleAdapter
import com.devshish.internship.presentation.ui.home.charts.tracks.TrackItemAdapter
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val artistItemAdapter = ArtistItemAdapter { viewModel.onArtistOrTrackClick(it.url) }
        val trackItemAdapter = TrackItemAdapter { viewModel.onArtistOrTrackClick(it.url) }
        val artistsTitleAdapter = ChartTitleAdapter(getString(R.string.home_chart_artists))
        val tracksTitleAdapter = ChartTitleAdapter(getString(R.string.home_chart_tracks))

        val concatAdapter = ConcatAdapter(
            artistsTitleAdapter,
            artistItemAdapter,
            tracksTitleAdapter,
            trackItemAdapter
        )

        with(binding) {
            layoutCharts.rvItems.apply {
                adapter = concatAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }

            layoutSwipeRefresh.setOnRefreshListener { viewModel.refreshCharts() }
        }

        with(viewModel) {
            topArtists.observe(viewLifecycleOwner) { artists ->
                artistItemAdapter.submitList(artists)
            }

            topTracks.observe(viewLifecycleOwner) { tracks ->
                trackItemAdapter.submitList(tracks)
            }

            navigationEvent.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { url ->
                    val action = HomeFragmentDirections.actionHomeFragmentToWebFragment(url)
                    findNavController().navigate(action)
                }
            }

            isLayoutRefreshing.observe(viewLifecycleOwner) { isRefreshing ->
                binding.layoutSwipeRefresh.isRefreshing = isRefreshing
            }
        }
    }
}

