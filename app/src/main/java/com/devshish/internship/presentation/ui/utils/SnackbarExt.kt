package com.devshish.internship.presentation.ui.utils

import android.view.View
import com.devshish.internship.R
import com.google.android.material.snackbar.Snackbar

fun Snackbar.set(action: View.OnClickListener?) {
    setAnchorView(R.id.bottomNavView)
    setAction(R.string.internet_connection_retry, action)
    show()
}