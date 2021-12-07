package com.devshish.internship.presentation.ui.utils

import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.SearchView
import com.devshish.internship.R
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

inline fun SearchView.onQueryTextChanged(crossinline listener: (String) -> Unit) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            listener(query.orEmpty())
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return true
        }
    })
}

fun View.showSnackbar(
    @StringRes messageRes: Int,
    @BaseTransientBottomBar.Duration duration: Int = Snackbar.LENGTH_LONG,
    @IdRes anchorViewRes: Int = R.id.bottomNavView,
    action: Pair<Int, () -> Unit>? = null
) {
    val snackbar = Snackbar.make(this, messageRes, duration)
        .setAnchorView(anchorViewRes)
    action?.let { (actionMessageRes, block) ->
        snackbar.setAction(actionMessageRes) { block() }
    }
    snackbar.show()
}
