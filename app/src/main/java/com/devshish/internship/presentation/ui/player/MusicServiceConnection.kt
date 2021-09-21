package com.devshish.internship.presentation.ui.player

import android.content.ComponentName
import android.content.Context
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devshish.internship.R
import com.devshish.internship.presentation.ui.utils.isPlayEnabled
import com.devshish.internship.presentation.ui.utils.isPlaying
import com.devshish.internship.presentation.ui.utils.isPrepared
import timber.log.Timber

class MusicServiceConnection(
    context: Context
) {

    val playbackState: LiveData<PlaybackStateCompat?>
        get() = _playbackState
    private val _playbackState = MutableLiveData<PlaybackStateCompat?>()

    var isPlaying: ((Unit) -> Boolean)? = null

    val curPlayingSong: LiveData<MediaMetadataCompat?>
        get() = _curPlayingSong
    private val _curPlayingSong = MutableLiveData<MediaMetadataCompat?>()

    private val mediaBrowserConnectionCallback =
        MediaBrowserConnectionCallback(context, this)

    private val mediaBrowser = MediaBrowserCompat(
        context,
        ComponentName(context, MusicService::class.java),
        mediaBrowserConnectionCallback,
        null
    ).apply { connect() }

    val mediaController: MediaControllerCompat by lazy {
        MediaControllerCompat(
            context,
            mediaBrowser.sessionToken
        )
    }

    val transportControls: MediaControllerCompat.TransportControls
        get() = mediaController.transportControls

    fun subscribe(parentId: String, callback: MediaBrowserCompat.SubscriptionCallback) {
        mediaBrowser.subscribe(parentId, callback)
    }

    fun unsubscribe(parentId: String, callback: MediaBrowserCompat.SubscriptionCallback) {
        mediaBrowser.unsubscribe(parentId, callback)
    }

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
            serviceConnection._playbackState.postValue(state)
            /*if (state?.isPrepared == true) {
                serviceConnection.isPlaying = state.isPrepared
            }
            if (state?.isPlaying == true) {
                serviceConnection.isPlaying = state.isPlaying
            }
            if (state?.isPlayEnabled == true) {
                serviceConnection.isPlaying = state.isPlayEnabled
            }*/

            serviceConnection.isPlaying?.let { it(serviceConnection._playbackState.postValue(state)) }

            Timber.d("$state -- ${serviceConnection.isPlaying}")
        }

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            serviceConnection._curPlayingSong.postValue(metadata)
        }

        override fun onSessionDestroyed() {
            serviceConnection.mediaBrowserConnectionCallback
                .onConnectionSuspended()
        }
    }
}
