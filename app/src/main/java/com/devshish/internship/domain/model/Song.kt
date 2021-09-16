package com.devshish.internship.domain.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Song(
    val mediaId: String?,
    val uri: Uri?,
    val title: String,
    val artist: String,
    val duration: Int,
    val imageUri: Uri?
) : Parcelable
