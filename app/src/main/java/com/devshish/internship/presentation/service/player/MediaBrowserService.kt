package com.devshish.internship.presentation.service.player

import android.app.PendingIntent
import android.os.Build
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat.*
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
        private const val REQUEST_CODE = 11
    }

    override fun onCreate() {
        super.onCreate()

        val activityIntent = packageManager.getLaunchIntentForPackage(packageName).let {
            PendingIntent.getActivity(
                baseContext,
                REQUEST_CODE,
                it,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                } else {
                    PendingIntent.FLAG_UPDATE_CURRENT
                }
            )
        }

        mediaSession = MediaSessionCompat(baseContext, "session_tag").apply {
            stateBuilder = Builder()
                .setActions(ACTION_PLAY or ACTION_PLAY_PAUSE)
            setPlaybackState(stateBuilder.build())

            setSessionActivity(activityIntent)

            val notificationManager = PlayerNotificationManager(
                context = this@MediaBrowserService,
                mediaSession = this
            )

            setCallback(
                MediaSessionCallback(
                    mediaService = this@MediaBrowserService,
                    mediaSession = this,
                    stateBuilder = stateBuilder,
                    exoPlayer = exoPlayer,
                    notificationManager = notificationManager
                )
            )
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
