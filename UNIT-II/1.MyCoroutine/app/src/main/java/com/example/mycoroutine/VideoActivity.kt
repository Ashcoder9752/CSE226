package com.example.mycoroutine

import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.mycoroutine.databinding.ActivityVideoBinding
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.request.get
import io.ktor.client.statement.readBytes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class VideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoBinding
    private val client = HttpClient(Android)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.downloadButton.setOnClickListener {
            val downloadLink = binding.textInputEditText.text.toString()

            // Check if the download link is not empty or just spaces
            if (downloadLink.isNotEmpty() && downloadLink.isNotBlank()) {
                downloadAndPlayVideo(downloadLink)
            } else {
                showToast("Please enter a valid download link")
            }
        }
    }

    private fun downloadAndPlayVideo(url: String) {
        // Launch a coroutine to handle the video download and processing on the IO dispatcher
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val videoFile = downloadVideoFile(url)

                // Update the UI with the downloaded video file on the main thread
                withContext(Dispatchers.Main) {
                    if (videoFile != null) {
                        // Uri is a class that represents a unique identifier for a resource
                        // It is used to identify and access the resource in the Android system
                        // such as a video file or a drawable image or network location
                        playVideo(Uri.fromFile(videoFile))

                        // or simply
                        // videoView.setVideoPath(videoFile.absolutePath)
                        // videoView.start()
                    } else {
                        showToast("Failed to download video")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showToast("Error: ${e.message}")
                }
            }
        }
    }

    // Function to download the video file from the provided URL
    private suspend fun downloadVideoFile(url: String): File? {
        try {
            val response = client.get(url) // Make an HTTP GET request to the provided URL
            val bytes = response.readBytes() // Read the response content as bytes

            // Create a File object to save the downloaded video in the cache directory
            val videoFile = File(cacheDir, "downloaded_video.mp4")
            videoFile.writeBytes(bytes) // Write the downloaded content to the file
            return videoFile // Return the File object representing the downloaded video
        } catch (e: Exception) {
            return null // Return null if there was an error downloading the video
        }
    }

    // Function to play the downloaded video
    private fun playVideo(uri: Uri) {
        val videoView = binding.videoView

        // Set an instance of MediaController to provide video playback controls (play, pause, etc.)
        val mediaController = MediaController(this)
        // This makes the controls appear over the video view
        mediaController.setAnchorView(videoView)

        videoView.setMediaController(mediaController)
        videoView.setVideoURI(uri) // Set the video URI to play
        videoView.requestFocus() // Makes sure the video view is ready to play

        // Set up listeners for video playback events
        videoView.setOnPreparedListener { videoView.start() }

        videoView.setOnCompletionListener { videoView.start() }

        videoView.setOnErrorListener { _, _, _ ->
            showToast("Error playing video")
            true // Return true to indicate that the error was handled
        }
    }

    // Function to show a toast message
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        client.close() // Close the HTTP client when the activity is destroyed
    }
}