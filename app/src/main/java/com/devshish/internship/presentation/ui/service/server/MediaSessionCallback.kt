package com.devshish.internship.presentation.ui.service.server

import android.net.Uri
import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.devshish.internship.presentation.ui.utils.song
import com.devshish.internship.presentation.ui.utils.toMediaMetadata
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import timber.log.Timber

class MediaSessionCallback(
    private val mediaSession: MediaSessionCompat,
    private val stateBuilder: PlaybackStateCompat.Builder,
    private val exoPlayer: ExoPlayer
) : MediaSessionCompat.Callback() {

    private fun MediaSessionCompat.updateState(
        stateBuilder: PlaybackStateCompat.Builder,
        state: Int,
        position: Long = 0L,
        speed: Float = 1f
    ): Unit = setPlaybackState(stateBuilder.setState(state, position, speed).build())

    override fun onPlayFromUri(uri: Uri?, extras: Bundle?) {
        super.onPlayFromUri(uri, extras)
        uri?.let {
            exoPlayer.apply {
                setMediaItem(MediaItem.fromUri(it))
                prepare()
                seekTo(0L)
                playWhenReady = true
            }
        }
        mediaSession.setMetadata(extras?.song?.toMediaMetadata())
        mediaSession.updateState(stateBuilder, PlaybackStateCompat.STATE_PLAYING)
        Timber.d("OnPlayFromUri: $uri")
    }

    override fun onPlay() {
        super.onPlay()
        exoPlayer.play()
        mediaSession.updateState(stateBuilder, PlaybackStateCompat.STATE_PLAYING)
        Timber.d("onPlay")
    }

    override fun onPause() {
        super.onPause()
        exoPlayer.pause()
        mediaSession.updateState(stateBuilder, PlaybackStateCompat.STATE_PAUSED)
        Timber.d("onPause")
    }

    override fun onStop() {
        super.onStop()
        exoPlayer.stop()
        mediaSession.updateState(stateBuilder, PlaybackStateCompat.STATE_STOPPED)
        Timber.d("onStop")
    }
}
