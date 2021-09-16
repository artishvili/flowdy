package com.devshish.internship.presentation.ui.utils

import android.support.v4.media.MediaMetadataCompat
import com.devshish.internship.domain.model.Song

fun MediaMetadataCompat.toSong(): Song? {
    return description?.let {
        Song(
            it.mediaId ?: "",
            it.mediaUri,
            it.title.toString(),
            it.subtitle.toString(),
            it.describeContents(),
            it.iconUri
        )
    }
}