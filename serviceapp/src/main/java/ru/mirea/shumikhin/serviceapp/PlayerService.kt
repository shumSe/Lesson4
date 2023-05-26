package ru.mirea.shumikhin.serviceapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class PlayerService : Service() {

    private var mediaPlayer: MediaPlayer? = null
    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        mediaPlayer!!.start()
        mediaPlayer!!.setOnCompletionListener { stopForeground(STOP_FOREGROUND_DETACH) }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentText("Mr.Kitty is now playing")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Mr.Kitty")
            )
            .setContentTitle("After Dark")
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(
            CHANNEL_ID, "Shumikhin Notification", importance
        )
        channel.description = "MIREA Channel"
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.createNotificationChannel(channel)
        startForeground(1, builder.build())
        mediaPlayer = MediaPlayer.create(this, R.raw.mr_kitty_after_dark)
        mediaPlayer!!.isLooping = false
    }

    override fun onDestroy() {
        stopForeground(true)
        mediaPlayer!!.stop()
    }

    companion object {
        const val CHANNEL_ID = "ForegroundServiceChannel"
    }
}