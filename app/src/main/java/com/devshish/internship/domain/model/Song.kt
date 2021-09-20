package com.devshish.internship.domain.model

import android.net.Uri
import java.io.Serializable

data class Song(
    val mediaId: String?,
    val uri: Uri?,
    val title: String,
    val artist: String,
    val duration: Int,
    val imageUri: Uri?
) : Serializable
