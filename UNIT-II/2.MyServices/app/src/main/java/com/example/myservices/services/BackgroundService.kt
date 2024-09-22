package com.example.myservices.services

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.Toast

class BackgroundService : Service() {

    // Define a Handler to post messages to the UI thread
    private val handler = Handler(Looper.getMainLooper())
    private val interval = 5000L // 5 seconds

    // Define a Runnable to run periodically
    private val runnable = object : Runnable {
        override fun run() {
            showToast("Service running...")
            handler.postDelayed(this, interval) // Schedule the next run
        }
    }

    // Override the onStartCommand method to start the service
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showToast("Service started")
        // Start the periodic task
        handler.postDelayed(runnable, interval)
        return START_STICKY // Return START_STICKY to indicate that the service should be restarted if it is killed
    }

    // Override the onDestroy method to stop the service
    override fun onDestroy() {
        super.onDestroy()
        // Remove the periodic task
        handler.removeCallbacks(runnable)
        showToast("Service stopped")
    }

    // Override the onBind method to return null since this service does not support binding
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private fun showToast(message: String) {
        Toast.makeText(this@BackgroundService, message, Toast.LENGTH_SHORT).show()
    }
}