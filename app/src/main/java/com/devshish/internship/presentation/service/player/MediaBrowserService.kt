package com.devshish.internship.presentation.service.player

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat.*
import androidx.core.app.NotificationManagerCompat
import androidx.media.MediaBrowserServiceCompat
import com.devshish.internship.presentation.ui.utils.position
import com.google.android.exoplayer2.ExoPlayer
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject

class MediaBrowserService : MediaBrowserServiceCompat() {

    private val exoPlayer: ExoPlayer by inject()

    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var stateBuilder: Builder

    private val job = CoroutineScope(Dispatchers.Main)

    companion object {
        private const val DELAY = 100L
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()

        mediaSession = MediaSessionCompat(baseContext, "session_tag").apply {
            stateBuilder = Builder()
                .setActions(ACTION_PLAY or ACTION_PLAY_PAUSE)
            setPlaybackState(stateBuilder.build())
            setCallback(MediaSessionCallback(
                this@MediaBrowserService,
                mediaSession = this,
                stateBuilder = stateBuilder,
                exoPlayer = exoPlayer
            ) {
                with(NotificationManagerCompat.from(this@MediaBrowserService)) {
                    notify(1, it.build())
                }
            })
            setSessionToken(sessionToken)
        }

        val extras = Bundle()
        job.launch {
            while (true) {
                extras.position = exoPlayer.currentPosition
                mediaSession.setExtras(extras)
                delay(DELAY)
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "channel_name"
            val descriptionText = "channel_desc"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("channel_id", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
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

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
