package com.devshish.internship.presentation.ui.utils

import java.util.concurrent.TimeUnit.*

fun convertMillisToTime(millis: Int): String {
    val minutes = MILLISECONDS.toMinutes(millis.toLong())
    val seconds = MILLISECONDS.toSeconds(millis.toLong()) - MINUTES.toSeconds(minutes)
    return String.format("%02d:%02d", minutes, seconds)
}
