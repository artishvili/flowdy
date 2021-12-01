package com.devshish.internship.presentation.service.player

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.media.session.MediaButtonReceiver
import com.devshish.internship.R
import com.devshish.internship.domain.model.Song

class PlayerNotificationManager(
    private val context: Context,
    mediaSession: MediaSessionCompat
) {

    companion object {
        private const val CHANNEL_ID = "channel_id"
        private const val NOTIFICATION_ID = 1
        private const val NOTIFICATION_CHANNEL_NAME = "Playback"
        private const val NOTIFICATION_CHANNEL_DESCRIPTION = "Internship playback"
    }

    init {
        createNotificationChannel()
    }

    private val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_music)
        .setContentIntent(mediaSession.controller.sessionActivity)
        .setDeleteIntent(setIntent(PlaybackStateCompat.ACTION_STOP))
        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        .setSilent(true)
        .setStyle(
            androidx.media.app.NotificationCompat.MediaStyle()
                .setMediaSession(mediaSession.sessionToken)
                .setShowActionsInCompactView(0)
                .setShowCancelButton(true)
                .setCancelButtonIntent(setIntent(PlaybackStateCompat.ACTION_STOP))
        ).also {
            it.color = ContextCompat.getColor(context, R.color.black)
        }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel =
                NotificationChannel(CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, importance).apply {
                    description = NOTIFICATION_CHANNEL_DESCRIPTION
                }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun Notification.show() {
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, this)
    }

    private fun Uri.toBitmap(): Bitmap? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(
                ImageDecoder.createSource(
                    context.contentResolver,
                    this
                )
            )
        } else {
            MediaStore.Images.Media.getBitmap(context.contentResolver, this)
        }
    }

    fun onFirstPlay(song: Song) {
        notificationBuilder
            .setContentTitle(song.title)
            .setContentText(song.artist)
            .setLargeIcon(song.imageUri?.toUri()?.toBitmap())
            .clearActions()
            .addAction(setActionAttr(R.drawable.exo_icon_pause))
            .build()
            .show()
    }

    fun onPlay() {
        notificationBuilder
            .clearActions()
            .addAction(setActionAttr(R.drawable.exo_icon_pause))
            .build()
            .show()
    }

    fun onPause() {
        notificationBuilder
            .clearActions()
            .addAction(setActionAttr(R.drawable.exo_icon_play))
            .build()
            .show()
    }

    private fun setIntent(action: Long): PendingIntent =
        MediaButtonReceiver.buildMediaButtonPendingIntent(
            context,
            action
        )

    private fun setActionAttr(@DrawableRes icon: Int): NotificationCompat.Action =
        NotificationCompat.Action(
            icon,
            null,
            setIntent(PlaybackStateCompat.ACTION_PLAY_PAUSE)
        )
}
