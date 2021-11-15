package com.devshish.internship.presentation.service.player.client

import android.os.Bundle
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.devshish.internship.domain.model.Song
import com.devshish.internship.presentation.ui.utils.position
import com.devshish.internship.presentation.ui.utils.toSong
import timber.log.Timber

class MediaControllerCallback(
    private val songCallback: (Song) -> Unit,
    private val isPlaying: (Boolean) -> Unit,
    private val currentPosition: ((Long) -> Unit),
    private val playerBarVisibility: (Boolean) -> Unit
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
            PlaybackStateCompat.STATE_PLAYING -> {
                isPlaying(true)
                playerBarVisibility(true)
            }
            PlaybackStateCompat.STATE_STOPPED -> playerBarVisibility(false)
            else -> isPlaying(false)
        }
    }

    override fun onExtrasChanged(extras: Bundle?) {
        super.onExtrasChanged(extras)
        extras?.let {
            currentPosition(it.position)
        }
    }
}
