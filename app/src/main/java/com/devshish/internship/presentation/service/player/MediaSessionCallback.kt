package com.devshish.internship.presentation.service.player

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.media.session.MediaButtonReceiver
import com.devshish.internship.R
import com.devshish.internship.presentation.ui.utils.song
import com.devshish.internship.presentation.ui.utils.toMediaMetadata
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import timber.log.Timber

class MediaSessionCallback(
    private val context: Context,
    private val mediaSession: MediaSessionCompat,
    private val stateBuilder: PlaybackStateCompat.Builder,
    private val exoPlayer: ExoPlayer,
    private val notificationCreateEvent: (NotificationCompat.Builder) -> Unit
) : MediaSessionCompat.Callback() {

    private val notificationCompatBuilder = NotificationCompat.Builder(context, "channel_id")
        .createNotification()

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

    private fun NotificationCompat.Builder.createNotification(): NotificationCompat.Builder {
        return apply {
            val controller = mediaSession.controller

            setContentIntent(controller.sessionActivity)

            setDeleteIntent(
                MediaButtonReceiver.buildMediaButtonPendingIntent(
                    context,
                    PlaybackStateCompat.ACTION_STOP
                )
            )

            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

            setSmallIcon(R.drawable.ic_music)
            color = ContextCompat.getColor(context, R.color.black)

            setSilent(true)

            setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(mediaSession.sessionToken)
                    .setShowActionsInCompactView(0)
                    .setShowCancelButton(true)
                    .setCancelButtonIntent(
                        MediaButtonReceiver.buildMediaButtonPendingIntent(
                            context,
                            PlaybackStateCompat.ACTION_STOP
                        )
                    )

            )
        }
    }

    override fun onPlayFromUri(uri: Uri?, extras: Bundle?) {
        super.onPlayFromUri(uri, extras)
        if (uri == null || extras == null) return
        exoPlayer.setAndPlaySong(uri)
        mediaSession.setMetadata(extras.song?.toMediaMetadata())
        mediaSession.updateState(
            stateBuilder = stateBuilder,
            state = PlaybackStateCompat.STATE_PLAYING
        )
        Timber.d("OnPlayFromUri: $uri")

        notificationCompatBuilder.clearActions()
        notificationCreateEvent(
            notificationCompatBuilder
                .setContentTitle(extras.song?.title)
                .setContentText(extras.song?.artist)
                .addAction(
                    NotificationCompat.Action(
                        R.drawable.exo_icon_pause,
                        null,
                        MediaButtonReceiver.buildMediaButtonPendingIntent(
                            context,
                            PlaybackStateCompat.ACTION_PLAY_PAUSE
                        )
                    )
                )
        )
    }

    override fun onPlay() {
        super.onPlay()
        mediaSession.updateState(
            stateBuilder = stateBuilder,
            state = PlaybackStateCompat.STATE_PLAYING
        )
        exoPlayer.play()
        Timber.d("onPlay")

        notificationCompatBuilder.clearActions()
        notificationCreateEvent(
            notificationCompatBuilder
                .addAction(
                    NotificationCompat.Action(
                        R.drawable.exo_icon_pause,
                        null,
                        MediaButtonReceiver.buildMediaButtonPendingIntent(
                            context,
                            PlaybackStateCompat.ACTION_PLAY_PAUSE
                        )
                    )
                )
        )
    }

    override fun onPause() {
        super.onPause()
        mediaSession.updateState(
            stateBuilder = stateBuilder,
            state = PlaybackStateCompat.STATE_PAUSED
        )
        exoPlayer.pause()
        Timber.d("onPause")

        notificationCompatBuilder.clearActions()
        notificationCreateEvent(
            notificationCompatBuilder
                .addAction(
                    NotificationCompat.Action(
                        R.drawable.exo_icon_play,
                        null,
                        MediaButtonReceiver.buildMediaButtonPendingIntent(
                            context,
                            PlaybackStateCompat.ACTION_PLAY_PAUSE
                        )
                    )
                )
        )
    }

    override fun onStop() {
        super.onStop()
        exoPlayer.stop()
        mediaSession.updateState(
            stateBuilder = stateBuilder,
            state = PlaybackStateCompat.STATE_STOPPED
        )
        Timber.d("onStop")
    }

    override fun onSeekTo(pos: Long) {
        super.onSeekTo(pos)
        exoPlayer.seekTo(pos)
    }
}
