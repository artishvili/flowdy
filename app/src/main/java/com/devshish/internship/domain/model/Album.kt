package com.devshish.internship.domain.model

import android.net.Uri

data class Album(
    val title: String,
    val artist: String,
    val cover: Uri?
)
