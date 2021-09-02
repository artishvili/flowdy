package com.devshish.internship.domain.model

import android.net.Uri

data class Song(
    val uri: Uri?,
    val title: String,
    val artist: String,
    val duration: Int,
    val imageUrl: String?
)
