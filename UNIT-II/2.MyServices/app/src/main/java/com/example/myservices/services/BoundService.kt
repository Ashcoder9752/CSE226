package com.example.myservices.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.Toast

class BoundService : Service() {

    // Binder object that allows clients to interact with this service.
    private val binder = MyBinder()

    // Inner class that defines the Binder for this service.
    inner class MyBinder : Binder() {
        fun getService(): BoundService = this@BoundService // Return the service instance
    }

    // Override the onBind method to return the binder object.
    override fun onBind(intent: Intent): IBinder {
        showToast("Service bound")
        return binder
    }

    // Called when a client unbinds from this service.
    override fun onUnbind(intent: Intent?): Boolean {
        showToast("Service unbound")
        // Call the superclass method for additional unbinding logic
        return super.onUnbind(intent) // Return true to allow rebinding
    }

    // Public method that can be called by clients from outside the service
    fun getRandomNumber(): Int {
        return (1..100).random()
    }

    private fun showToast(message: String) {
        Toast.makeText(this@BoundService, message, Toast.LENGTH_SHORT).show()
    }
}