package com.devshish.internship.domain.model

import android.net.Uri

data class SearchSong(
    val title: String,
    val artist: String,
    val imageUri: Uri?
)
