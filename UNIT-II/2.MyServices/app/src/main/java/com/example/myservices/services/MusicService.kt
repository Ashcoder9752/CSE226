package com.example.myservices.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.myservices.MainActivity

class MusicService : Service() {

    // Define constants for the notification channel and notification ID
    companion object {
        const val CHANNEL_ID = "music_channel"
        const val NOTIFICATION_ID = 1
    }

    // Override the onCreate method to perform any necessary initialization
    override fun onCreate() {
        super.onCreate()
        // Create a notification channel for Android Oreo and above
        // This is required for displaying notifications in the foreground service in Android Oreo and above
        // You can customize the notification channel as needed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create a notification channel
            val channel = NotificationChannel(
                CHANNEL_ID, // Channel ID
                "Music Channel", // Channel name
                NotificationManager.IMPORTANCE_DEFAULT // Importance level
            )
            // Create a notification manager and create the channel
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    // Override the onStartCommand method to start the foreground service
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Create an intent that will be triggered when the notification is tapped
        val notificationIntent = Intent(this@MusicService, MainActivity::class.java)
        // Create a pending intent for the notification
        val pendingIntent = PendingIntent.getActivity(
            this@MusicService,
            0, // Request code, not used in this example
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE // Flag to make the pending intent immutable
        )

        // Create a notification using NotificationCompat.Builder
        val notification = NotificationCompat.Builder(this@MusicService, CHANNEL_ID)
            .setContentTitle("Music Player")
            .setContentText("Playing music...")
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setContentIntent(pendingIntent) // Set the pending intent for the notification content, which will be triggered when tapped
            .build()

        // Start the service in the foreground with the notification
        startForeground(NOTIFICATION_ID, notification)
        showToast("Music Service Started")

        // Return the start ID to indicate how to behave when the service is stopped
        // In this case, the service will be restarted if it is killed
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        showToast("Music Service Stopped")
    }

    // Override the onBind method to return null since this service does not support binding
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private fun showToast(message: String) {
        Toast.makeText(this@MusicService, message, Toast.LENGTH_SHORT).show()
    }
}