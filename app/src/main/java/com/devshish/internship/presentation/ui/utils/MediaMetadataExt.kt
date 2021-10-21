package com.devshish.internship.presentation.ui.utils

import android.support.v4.media.MediaMetadataCompat
import com.devshish.internship.domain.model.Song

private const val SONG_URI = "SONG_URI"
private const val SONG_TITLE = "SONG_TITLE"
private const val SONG_ARTIST = "SONG_ARTIST"
private const val SONG_DURATION = "SONG_DURATION"
private const val SONG_IMAGE_URI = "SONG_IMAGE_URI"

fun MediaMetadataCompat.toSong(): Song {
    return Song(
        uri = getString(SONG_URI),
        title = getString(SONG_TITLE),
        artist = getString(SONG_ARTIST),
        duration = getString(SONG_DURATION).toInt(),
        imageUri = getString(SONG_IMAGE_URI)
    )
}

fun Song.toMediaMetadata(): MediaMetadataCompat {
    return MediaMetadataCompat.Builder()
        .putString("SONG_URI", uri)
        .putString("SONG_TITLE", title)
        .putString("SONG_ARTIST", artist)
        .putString("SONG_DURATION", duration.toString())
        .putString("SONG_IMAGE_URI", imageUri)
        .build()
}
