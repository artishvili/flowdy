package com.devshish.internship.presentation.ui.utils

import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import com.devshish.internship.R
import com.google.android.material.snackbar.Snackbar

fun Snackbar.caseSmthWentWrong(
    @IdRes anchorViewRes: Int = R.id.bottomNavView,
    @StringRes actionMessageRes: Int = R.string.snackbar_retry,
    action: ((View) -> Unit)?
) {
    setAnchorView(anchorViewRes)
    setAction(actionMessageRes, action)
    show()
}
