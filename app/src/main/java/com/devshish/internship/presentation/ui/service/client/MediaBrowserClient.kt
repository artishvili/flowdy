package com.devshish.internship.presentation.ui.service.client

import android.content.ComponentName
import android.content.Context
import android.net.Uri
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.devshish.internship.presentation.ui.service.server.MediaBrowserService

class MediaBrowserClient(context: Context) {

    private lateinit var mediaBrowser: MediaBrowserCompat
    private lateinit var mediaController: MediaControllerCompat

    private val controllerCallback = MediaControllerCallback()

    var isPlaying = false

    init {
        mediaBrowser = MediaBrowserCompat(
            context,
            ComponentName(context, MediaBrowserService::class.java),
            object : MediaBrowserCompat.ConnectionCallback() {
                override fun onConnected() {
                    super.onConnected()
                    mediaController =
                        MediaControllerCompat(context, mediaBrowser.sessionToken).apply {
                            registerCallback(controllerCallback)
                        }
                }
            },
            null
        )
    }

    fun playFromUri(uri: Uri?) {
        mediaController.transportControls.playFromUri(uri, null)
        isPlaying = true
    }

    fun toggle() {
        val pbState = mediaController.playbackState.state
        if (pbState == PlaybackStateCompat.STATE_PLAYING) {
            mediaController.transportControls.pause()
            isPlaying = false
        } else {
            mediaController.transportControls.play()
            isPlaying = true
        }
    }

    fun connect() = mediaBrowser.connect()

    fun disconnect() = mediaBrowser.disconnect()

    fun unregister() = mediaController.unregisterCallback(controllerCallback)
}
