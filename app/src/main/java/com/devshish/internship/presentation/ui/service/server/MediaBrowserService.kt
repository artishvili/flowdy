package com.devshish.internship.presentation.ui.service.server

import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat.*
import androidx.media.MediaBrowserServiceCompat
import com.devshish.internship.presentation.ui.utils.position
import com.google.android.exoplayer2.ExoPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MediaBrowserService : MediaBrowserServiceCompat() {

    private val exoPlayer: ExoPlayer by inject()

    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var stateBuilder: Builder

    override fun onCreate() {
        super.onCreate()

        mediaSession = MediaSessionCompat(baseContext, "session_tag").apply {
            stateBuilder = Builder()
                .setActions(ACTION_PLAY or ACTION_PLAY_PAUSE)
            setPlaybackState(stateBuilder.build())
            setCallback(MediaSessionCallback(this, stateBuilder, exoPlayer))
            setSessionToken(sessionToken)
        }

        CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                val extras = Bundle().also {
                    it.position = exoPlayer.currentPosition
                }
                mediaSession.setExtras(extras)
                delay(100L)
            }
        }
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot = BrowserRoot("root_id", null)

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ): Unit = result.sendResult(null)
}
