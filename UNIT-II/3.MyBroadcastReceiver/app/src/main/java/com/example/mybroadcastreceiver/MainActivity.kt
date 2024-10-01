package com.example.mybroadcastreceiver

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.BatteryManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // Declare UI components
    private lateinit var airplaneModeStatus: TextView
    private lateinit var ringerModeStatus: TextView
    private lateinit var internetStatus: TextView
    private lateinit var batteryStatus: TextView
    private lateinit var refreshButton: Button

    // Declare receiver and connectivity manager
    private lateinit var receiver: ModeChangeReceiver
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        airplaneModeStatus = findViewById(R.id.airplaneModeStatus)
        ringerModeStatus = findViewById(R.id.ringerModeStatus)
        internetStatus = findViewById(R.id.internetStatus)
        batteryStatus = findViewById(R.id.batteryStatus)
        refreshButton = findViewById(R.id.refreshButton)

        // Initialize receiver
        receiver = ModeChangeReceiver { status, message ->
            when (status) {
                "Airplane Mode" -> airplaneModeStatus.text = "Airplane Mode: $message"
                "Ringer Mode" -> ringerModeStatus.text = "Ringer Mode: $message"
                "Internet Status" -> internetStatus.text = "Internet Status: $message"
                "Battery Status" -> batteryStatus.text = "Battery Status: $message"
            }
        }

        // Set the refresh button to manually update the status when clicked
        refreshButton.setOnClickListener {
            updateStatus()
        }

        // Register the broadcast receivers to listen for system events
        registerReceivers()
        // Set up network callback to monitor internet connectivity changes
        setupNetworkCallback()
    }

    // Method to register broadcast receivers for airplane mode, ringer mode, and battery status
    private fun registerReceivers() {
        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
            addAction(AudioManager.RINGER_MODE_CHANGED_ACTION)
            addAction(Intent.ACTION_BATTERY_CHANGED)
        }
        registerReceiver(receiver, intentFilter)
    }

    // Set up a callback to listen for network (internet) availability changes
    private fun setupNetworkCallback() {
        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        networkCallback = object : ConnectivityManager.NetworkCallback() {
            // Update internet status when the network is available
            override fun onAvailable(network: android.net.Network) {
                updateInternetStatus(true)
            }

            // Update internet status when the network is lost
            override fun onLost(network: android.net.Network) {
                updateInternetStatus(false)
            }
        }

        // Register the default network callback to listen for internet status changes
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    // Update the UI for the internet status (connected or disconnected)
    private fun updateInternetStatus(isConnected: Boolean) {
        internetStatus.text = "Internet Status: ${if (isConnected) "Connected" else "Disconnected"}"
    }

    // Update the statuses for airplane mode, battery, and ringer mode when the refresh button is clicked
    private fun updateStatus() {
        // Create an IntentFilter to capture the current airplane mode and ringer mode statuses
        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
            addAction(AudioManager.RINGER_MODE_CHANGED_ACTION)
            addAction(Intent.ACTION_BATTERY_CHANGED)
        }
        // Get the most recent system broadcast and pass it to the receiver
        val intent = registerReceiver(null, intentFilter)
        intent?.let { receiver.onReceive(this@MainActivity, it) }

        // Check airplane mode status and update the UI
        val isAirplaneModeOn = Settings.Global.getInt(
            contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0
        ) != 0
        airplaneModeStatus.text =
            "Airplane Mode: ${if (isAirplaneModeOn) "Enabled" else "Disabled"}"

        // Check battery status and update the UI
        val batteryIntent = registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        batteryIntent?.let { battery ->
            val level = battery.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale = battery.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            val batteryPercentage = (level.toFloat() / scale.toFloat() * 100).toInt()

            val status = battery.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
            val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL
            val chargingStatus = if (isCharging) "Charging" else "Not Charging"
            batteryStatus.text = "Battery Status: $batteryPercentage% ($chargingStatus)"
        }
    }

    // Unregister the broadcast receiver and network callback when the activity is destroyed
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}