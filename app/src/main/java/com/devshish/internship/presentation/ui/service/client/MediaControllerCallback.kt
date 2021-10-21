package com.devshish.internship.presentation.ui.service.client

import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.devshish.internship.domain.model.Song
import com.devshish.internship.presentation.ui.utils.toSong
import timber.log.Timber

class MediaControllerCallback(
    private val songCallback: (Song) -> Unit,
    private val isPlaying: (Boolean) -> Unit
) : MediaControllerCompat.Callback() {

    override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
        super.onMetadataChanged(metadata)
        Timber.d("Metadata: $metadata")
        metadata?.let {
            songCallback(it.toSong())
        }
    }

    override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
        super.onPlaybackStateChanged(state)
        Timber.d("State: $state")
        if (state == null) return
        when (state.state) {
            PlaybackStateCompat.STATE_PLAYING -> isPlaying(true)
            PlaybackStateCompat.STATE_PAUSED -> isPlaying(false)
            PlaybackStateCompat.STATE_REWINDING -> isPlaying(false)
            PlaybackStateCompat.STATE_STOPPED -> isPlaying(false)
        }
    }
}
