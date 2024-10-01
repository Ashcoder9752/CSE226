package com.example.mybroadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.BatteryManager

class ModeChangeReceiver(private val statusCallback: (String, String) -> Unit) :
    BroadcastReceiver() {
    // This method is triggered when the BroadcastReceiver receives a broadcast for a specific intent/action.
    override fun onReceive(ctx: Context?, intent: Intent?) {
        // Ensure the context is not null. If null, return early and do nothing.
        val context = ctx ?: return

        // Check the action of the broadcast and handle accordingly.
        when (intent?.action) {
            // If the action is ACTION_AIRPLANE_MODE_CHANGED, it means the airplane mode has changed.
            Intent.ACTION_AIRPLANE_MODE_CHANGED -> {
                // Get the current airplane mode state and convert it to a human-readable string.
                val isAirplaneModeOn = intent.getBooleanExtra("state", false)
                // Call the statusCallback function with the appropriate information.
                statusCallback("Airplane Mode", if (isAirplaneModeOn) "Enabled" else "Disabled")
            }

            // If the action is RINGER_MODE_CHANGED_ACTION, it means the ringer mode has changed.
            AudioManager.RINGER_MODE_CHANGED_ACTION -> {
                // Get the current ringer mode and convert it to a human-readable string.
                val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
                val message = when (audioManager.ringerMode) {
                    AudioManager.RINGER_MODE_SILENT -> "Silent"
                    AudioManager.RINGER_MODE_VIBRATE -> "Vibrate"
                    AudioManager.RINGER_MODE_NORMAL -> "Normal"
                    else -> "Unknown"
                }
                // Call the statusCallback function with the appropriate information.
                statusCallback("Ringer Mode", message)
            }

            // If the action is ACTION_BATTERY_CHANGED, it means the battery status has changed.
            Intent.ACTION_BATTERY_CHANGED -> {
                // Get the current battery level and status, calculate a percentage, and determine if it's charging or not.
                val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                val batteryPercentage = (level.toFloat() / scale.toFloat() * 100).toInt()
                val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
                val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL
                // Create a message indicating the battery percentage and charging status.
                val chargingStatus = if (isCharging) "Charging" else "Not Charging"
                val message = "$batteryPercentage% ($chargingStatus)"
                // Call the statusCallback function with the appropriate information.
                statusCallback("Battery Status", message)
            }
        }
    }
}