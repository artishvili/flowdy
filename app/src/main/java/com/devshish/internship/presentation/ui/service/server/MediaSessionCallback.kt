package com.devshish.internship.presentation.ui.service.server

import android.net.Uri
import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import timber.log.Timber

class MediaSessionCallback(
    private val mediaSession: MediaSessionCompat,
    private val stateBuilder: PlaybackStateCompat.Builder
) : MediaSessionCompat.Callback() {

    private fun setState(
        mediaSession: MediaSessionCompat,
        stateBuilder: PlaybackStateCompat.Builder,
        state: Int,
        position: Long = 0L,
        speed: Float = 1f
    ) = mediaSession.setPlaybackState(
        stateBuilder.setState(state, position, speed).build()
    )

    override fun onPlayFromUri(uri: Uri?, extras: Bundle?) {
        super.onPlayFromUri(uri, extras)
        setState(mediaSession, stateBuilder, PlaybackStateCompat.STATE_PLAYING)
        Timber.d("OnPlayFromUri: $uri")
    }

    override fun onPlay() {
        super.onPlay()
        setState(mediaSession, stateBuilder, PlaybackStateCompat.STATE_PLAYING)
        Timber.d("onPlay")
    }

    override fun onPause() {
        super.onPause()
        setState(mediaSession, stateBuilder, PlaybackStateCompat.STATE_PAUSED)
        Timber.d("onPause")
    }

    override fun onStop() {
        super.onStop()
        setState(mediaSession, stateBuilder, PlaybackStateCompat.STATE_STOPPED)
        Timber.d("onStop")
    }
}
