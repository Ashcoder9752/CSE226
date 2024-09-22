package com.example.myservices

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myservices.databinding.ActivityMainBinding
import com.example.myservices.services.BackgroundService
import com.example.myservices.services.BoundService
import com.example.myservices.services.MusicService

class MainActivity : AppCompatActivity() {

    companion object {
        const val PERMISSION_REQUEST_CODE = 101 // Replace with your desired request code
    }

    private var boundService: BoundService? = null // Reference to the bound service
    private var isBound = false // Flag to track if the service is bound

    // Define a ServiceConnection to manage the connection to the service
    private val serviceConnection = object : ServiceConnection {
        // Called when the connection to the service is established
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            // Cast the IBinder to our custom MyBinder class
            val binder = service as BoundService.MyBinder
            boundService = binder.getService() // Get the service instance
            isBound = true // Set the bound flag to true
        }

        // Called when the service is unexpectedly disconnected
        override fun onServiceDisconnected(name: ComponentName?) {
            boundService = null // Reset the bound service reference
            isBound = false // Set the bound flag to false
        }
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // FOREGROUND SERVICE

        // For Android 13 and above, check for notification permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this@MainActivity,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_DENIED
            ) { // If permission is not granted, request it
                requestPermissions(
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    PERMISSION_REQUEST_CODE
                )
            }
        }

        // Start the service when the button is clicked
        binding.startForegroundService.setOnClickListener {
            // For Android 13 and above, check for notification permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(
                        this@MainActivity,
                        android.Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    // Permission is granted, start the service
                    startService(Intent(this@MainActivity, MusicService::class.java))
                } else {
                    showToast("Notification permission denied") // Show a toast message if permission is denied

                    // Request the permission again if it's not granted
                    requestPermissions(
                        arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                        PERMISSION_REQUEST_CODE
                    )
                }
            } else {
                // Permission is not required for Android 13 and below, start the service
                startService(Intent(this@MainActivity, MusicService::class.java))
            }
        }

        binding.stopForegroundService.setOnClickListener {
            // Stop the service when the button is clicked
            stopService(Intent(this@MainActivity, MusicService::class.java))
        }


        // BACKGROUND SERVICE

        binding.startBackgroundService.setOnClickListener {
            // Start the service when the button is clicked
            startService(Intent(this@MainActivity, BackgroundService::class.java))
        }

        binding.stopBackgroundService.setOnClickListener {
            // Stop the service when the button is clicked
            stopService(Intent(this@MainActivity, BackgroundService::class.java))
        }


        // BOUND SERVICE

        // Start the service when the button is clicked
        binding.bindService.setOnClickListener {
            val intent = Intent(this@MainActivity, BoundService::class.java)
            bindService(
                intent,
                serviceConnection,
                BIND_AUTO_CREATE
            ) // Bind to the service using the serviceConnection
        }

        // Unbind from the service when the button is clicked
        binding.unbindService.setOnClickListener {
            if (isBound) { // Check if the service is already bound
                unbindService(serviceConnection) // Unbind from the service
                isBound = false // Set the bound flag to false
            } else {
                showToast("Service not bound") // Show a toast message if the service is not bound
            }
        }

        // Call the service method when the button is clicked
        binding.callServiceMethod.setOnClickListener {
            if (isBound) { // Check if the service is already bound
                val randomNumber = boundService?.getRandomNumber() // Call the service method
                showToast("Random Number: $randomNumber")
            } else {
                showToast("Service not bound") // Show a toast message if the service is not bound
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }
}