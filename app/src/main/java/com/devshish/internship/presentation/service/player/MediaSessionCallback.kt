package com.devshish.internship.presentation.service.player

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.devshish.internship.presentation.ui.utils.song
import com.devshish.internship.presentation.ui.utils.toMediaMetadata
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import timber.log.Timber

class MediaSessionCallback(
    private val mediaService: MediaBrowserService,
    private val mediaSession: MediaSessionCompat,
    private val stateBuilder: PlaybackStateCompat.Builder,
    private val exoPlayer: ExoPlayer,
    private val notificationManager: PlayerNotificationManager
) : MediaSessionCompat.Callback() {

    init {
        exoPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                if (playbackState == Player.STATE_ENDED) {
                    mediaSession.updateState(
                        stateBuilder = stateBuilder,
                        state = PlaybackStateCompat.STATE_STOPPED
                    )
                    notificationManager.onStop()
                    exoPlayer.stop()
                    mediaService.stopSelf()
                    Timber.d("onStop")
                }
            }
        })
    }

    private fun MediaSessionCompat.updateState(
        stateBuilder: PlaybackStateCompat.Builder,
        state: Int,
        position: Long = exoPlayer.currentPosition,
        speed: Float = 1f
    ): Unit = setPlaybackState(stateBuilder.setState(state, position, speed).build())

    private fun ExoPlayer.setAndPlaySong(uri: Uri) {
        setMediaItem(MediaItem.fromUri(uri))
        prepare()
        playWhenReady = true
    }

    override fun onPlayFromUri(uri: Uri?, extras: Bundle?) {
        super.onPlayFromUri(uri, extras)
        val song = extras?.song
        if (uri == null || song == null) return
        exoPlayer.setAndPlaySong(uri)
        mediaSession.setMetadata(song.toMediaMetadata())
        mediaSession.updateState(
            stateBuilder = stateBuilder,
            state = PlaybackStateCompat.STATE_PLAYING
        )
        notificationManager.onFirstPlay(song)
        mediaService.startService(Intent(mediaService.baseContext, MediaBrowserService::class.java))
        Timber.d("OnPlayFromUri: $uri")
    }

    override fun onPlay() {
        super.onPlay()
        mediaSession.updateState(
            stateBuilder = stateBuilder,
            state = PlaybackStateCompat.STATE_PLAYING
        )
        exoPlayer.play()
        notificationManager.onPlay()
        Timber.d("onPlay")
    }

    override fun onPause() {
        super.onPause()
        mediaSession.updateState(
            stateBuilder = stateBuilder,
            state = PlaybackStateCompat.STATE_PAUSED
        )
        exoPlayer.pause()
        notificationManager.onPause()
        Timber.d("onPause")
    }

    override fun onSeekTo(pos: Long) {
        super.onSeekTo(pos)
        exoPlayer.seekTo(pos)
    }
}
