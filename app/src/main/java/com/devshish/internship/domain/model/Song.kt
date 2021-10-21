package com.devshish.internship.domain.model

import java.io.Serializable

data class Song(
    val uri: String?,
    val title: String,
    val artist: String,
    val duration: Int,
    val imageUri: String?
) : Serializable
