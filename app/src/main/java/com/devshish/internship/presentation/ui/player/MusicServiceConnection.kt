package com.devshish.internship.presentation.ui.player

import android.content.ComponentName
import android.content.Context
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.devshish.internship.R
import com.devshish.internship.domain.model.Song
import com.devshish.internship.presentation.ui.utils.currentPlaybackPosition
import com.devshish.internship.presentation.ui.utils.isPlaying
import com.devshish.internship.presentation.ui.utils.toSong
import timber.log.Timber

class MusicServiceConnection(
    context: Context
) {

    var isPlaying: ((Boolean) -> Unit)? = null
    var currentSong: ((Song) -> Unit)? = null
    var currentPosition: ((Long) -> Unit)? = null

    private val mediaBrowserConnectionCallback =
        MediaBrowserConnectionCallback(context, this)

    val mediaController: MediaControllerCompat by lazy {
        MediaControllerCompat(
            context,
            mediaBrowser.sessionToken
        )
    }

    private val mediaBrowser = MediaBrowserCompat(
        context,
        ComponentName(context, MusicService::class.java),
        mediaBrowserConnectionCallback,
        null
    ).apply { connect() }

    val transportControls: MediaControllerCompat.TransportControls
        get() = mediaController.transportControls

    private class MediaBrowserConnectionCallback(
        private val context: Context,
        private val serviceConnection: MusicServiceConnection
    ) : MediaBrowserCompat.ConnectionCallback() {

        override fun onConnected() {
            serviceConnection.mediaController.apply {
                registerCallback(MediaControllerCallback(serviceConnection))
            }
        }

        override fun onConnectionSuspended() {
            Timber.i(context.getString(R.string.service_connection_suspended))
        }

        override fun onConnectionFailed() {
            Timber.i(context.getString(R.string.service_connection_failed))
        }
    }

    private class MediaControllerCallback(
        private val serviceConnection: MusicServiceConnection
    ) : MediaControllerCompat.Callback() {

        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            serviceConnection.isPlaying?.let { it(state!!.isPlaying) }
            serviceConnection.currentPosition?.let { it(state!!.position) }
        }

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            serviceConnection.currentSong?.let { it(metadata?.toSong()!!) }
        }

        override fun onSessionDestroyed() {
            serviceConnection.mediaBrowserConnectionCallback
                .onConnectionSuspended()
        }
    }
}
