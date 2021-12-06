package com.devshish.internship.presentation.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentHomeBinding
import com.devshish.internship.presentation.ui.home.charts.artists.ArtistItemAdapter
import com.devshish.internship.presentation.ui.home.charts.title.ChartTitleAdapter
import com.devshish.internship.presentation.ui.home.charts.tracks.TrackItemAdapter
import com.devshish.internship.presentation.ui.utils.caseSmthWentWrong
import com.google.android.material.snackbar.Snackbar
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
            exception.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { messageRes ->
                    Snackbar.make(requireView(), messageRes, Snackbar.LENGTH_LONG)
                        .caseSmthWentWrong { refreshCharts() }
                }
            }

            topArtists.observe(viewLifecycleOwner) { artists ->
                artistItemAdapter.submitList(artists)
            }

            topTracks.observe(viewLifecycleOwner) { tracks ->
                trackItemAdapter.submitList(tracks)
            }

            navigationEvent.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { url ->
                    val browserIntent = Intent(Intent.ACTION_VIEW, url.toUri())
                    startActivity(browserIntent)
                }
            }

            isLayoutRefreshing.observe(viewLifecycleOwner) { isRefreshing ->
                binding.layoutSwipeRefresh.isRefreshing = isRefreshing
            }
        }
    }
}

