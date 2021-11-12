package com.devshish.internship.domain.model

import android.net.Uri
import java.io.Serializable

data class SearchSong(
    val title: String,
    val artist: String,
    val imageUri: Uri?,
    val lyricsUri: String?,
    val lyrics: String?
) : Serializable
