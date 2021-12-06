package com.devshish.internship.presentation.ui.search

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentSearchBinding
import com.devshish.internship.presentation.ui.utils.onQueryTextChanged
import com.devshish.internship.presentation.ui.utils.set
import com.google.android.material.snackbar.Snackbar
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val viewModel: SearchViewModel by viewModel()

    private val searchSongAdapter = ItemSearchSongAdapter {
        viewModel.onSearchSongClick(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        with(binding) {
            rvSongs.apply {
                adapter = searchSongAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }

        with(viewModel) {
            songsList.observe(viewLifecycleOwner) {
                searchSongAdapter.submitList(it)
            }

            isProgressLoading.observe(viewLifecycleOwner) { isLoading ->
                if (isLoading) {
                    binding.progressIndicator.show()
                } else {
                    binding.progressIndicator.hide()
                }
            }

            isDescriptionVisible.observe(viewLifecycleOwner) { isVisible ->
                binding.tvDescription.isVisible = isVisible
            }

            navigateToLyricsEvent.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { song ->
                    val action = SearchFragmentDirections.actionSearchFragmentToLyricsFragment(song)
                    findNavController().navigate(action)
                }
            }

            noInternetConnection.observe(viewLifecycleOwner) {
                Snackbar.make(
                    requireView(),
                    R.string.internet_connection_absent,
                    Snackbar.LENGTH_LONG
                ).set(null)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.actionSearch -> {
                val searchView = item.actionView as SearchView
                searchView.onQueryTextChanged { query ->
                    viewModel.searchSongs(query)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
