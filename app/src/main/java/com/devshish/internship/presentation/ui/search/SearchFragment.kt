package com.devshish.internship.presentation.ui.search

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.devshish.internship.R
import com.devshish.internship.databinding.FragmentSearchBinding
import com.devshish.internship.presentation.ui.utils.onQueryTextChanged
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.itemSearch)
        val searchView = searchItem.actionView as SearchView

        searchView.onQueryTextChanged {
            // TODO
        }
    }
}
