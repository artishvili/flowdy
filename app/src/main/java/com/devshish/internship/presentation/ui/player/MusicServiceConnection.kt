package com.devshish.internship.presentation.ui.player

import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devshish.internship.R
import com.devshish.internship.presentation.ui.utils.Constants.NETWORK_ERROR
import com.devshish.internship.presentation.ui.utils.Event
import com.devshish.internship.presentation.ui.utils.Resource

class MusicServiceConnection(
    context: Context
) {

    val isConnected: LiveData<Event<Resource<Boolean>>>
        get() = _isConnected
    private val _isConnected = MutableLiveData<Event<Resource<Boolean>>>()

    val networkError: LiveData<Event<Resource<Boolean>>>
        get() = _networkError
    private val _networkError = MutableLiveData<Event<Resource<Boolean>>>()

    val playbackState: LiveData<PlaybackStateCompat?>
        get() = _playbackState
    private val _playbackState = MutableLiveData<PlaybackStateCompat?>()

    val curPlayingSong: LiveData<MediaMetadataCompat?>
        get() = _curPlayingSong
    private val _curPlayingSong = MutableLiveData<MediaMetadataCompat?>()

    lateinit var mediaController: MediaControllerCompat

    private val mediaBrowserConnectionCallback = MediaBrowserConnectionCallback(context)

    private val mediaBrowser = MediaBrowserCompat(
        context,
        ComponentName(context, MusicService::class.java),
        mediaBrowserConnectionCallback,
        null
    ).apply { connect() }

    val transportControls: MediaControllerCompat.TransportControls
        get() = mediaController.transportControls

    fun subscribe(parentId: String, callback: MediaBrowserCompat.SubscriptionCallback) {
        mediaBrowser.subscribe(parentId, callback)
    }

    fun unsubscribe(parentId: String, callback: MediaBrowserCompat.SubscriptionCallback) {
        mediaBrowser.unsubscribe(parentId, callback)
    }

    private inner class MediaBrowserConnectionCallback(
        private val context: Context
    ) : MediaBrowserCompat.ConnectionCallback() {

        override fun onConnected() {
            mediaController = MediaControllerCompat(context, mediaBrowser.sessionToken).apply {
                registerCallback(MediaControllerCallback(context))
            }
            _isConnected.postValue(Event(Resource.success(data = true)))
        }

        override fun onConnectionSuspended() {
            _isConnected.postValue(
                Event(
                Resource.error(
                message = context.getString(R.string.service_connection_suspended),
                data = false
            ))
            )
        }

        override fun onConnectionFailed() {
            _isConnected.postValue(
                Event(
                Resource.error(
                message = context.getString(R.string.service_connection_failed),
                data = false
            ))
            )
        }
    }

    private inner class MediaControllerCallback(
        private val context: Context
    ) : MediaControllerCompat.Callback() {

        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            _playbackState.postValue(state)
        }

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            _curPlayingSong.postValue(metadata)
        }

        override fun onSessionEvent(event: String?, extras: Bundle?) {
            super.onSessionEvent(event, extras)
            when (event) {
                NETWORK_ERROR -> _networkError.postValue(
                    Event(
                    Resource.error(
                    message = context.getString(R.string.service_connection_server_error),
                    data = null
                ))
                )
            }
        }

        override fun onSessionDestroyed() {
            mediaBrowserConnectionCallback.onConnectionSuspended()
        }
    }
}
