package com.devshish.internship.presentation.service.player.client

import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.net.toUri
import com.devshish.internship.domain.model.Song
import com.devshish.internship.presentation.service.player.MediaBrowserService
import com.devshish.internship.presentation.ui.utils.song

class MediaBrowserClient(context: Context) {

    private lateinit var mediaBrowser: MediaBrowserCompat
    private lateinit var mediaController: MediaControllerCompat

    var currentSongCallback: CurrentSongCallback? = null

    private val controllerCallback = MediaControllerCallback(
        { song -> currentSongCallback?.updateSong(song)  },
        { state -> currentSongCallback?.getState(state) },
        { position -> currentSongCallback?.getPosition(position) },
        { visibility -> currentSongCallback?.getPlayerBarVisibility(visibility) }
    )

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

    fun playSong(song: Song) {
        val extras = Bundle().also {
            it.song = song
        }
        mediaController.transportControls.playFromUri(song.uri?.toUri(), extras)
    }

    fun toggle() {
        val pbState = mediaController.playbackState.state
        if (pbState == PlaybackStateCompat.STATE_PLAYING) {
            mediaController.transportControls.pause()
        } else {
            mediaController.transportControls.play()
        }
    }

    fun seekTo(position: Long) {
        mediaController.transportControls.seekTo(position)
    }

    fun connect(): Unit = mediaBrowser.connect()

    fun disconnect(): Unit = mediaBrowser.disconnect()

    fun unregister(): Unit = mediaController.unregisterCallback(controllerCallback)

    interface CurrentSongCallback {

        fun updateSong(song: Song)

        fun getState(isPlaying: Boolean)

        fun getPosition(position: Long)

        fun getPlayerBarVisibility(isVisible: Boolean)
    }
}
