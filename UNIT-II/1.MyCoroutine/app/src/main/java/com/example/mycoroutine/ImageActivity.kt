package com.example.mycoroutine

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.mycoroutine.databinding.ActivityImageBinding
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.request.get
import io.ktor.client.statement.readBytes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageBinding
    private val client = HttpClient(Android)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityImageBinding.inflate(layoutInflater)
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
                downloadImage(downloadLink)
            } else {
                showToast("Please enter a valid download link")
            }
        }
    }

    // Function to download and process the image
    private fun downloadImage(url: String) {
        // Launch a coroutine to handle the image download and processing on the IO dispatcher
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val bitmap = downloadImageFile(url)

                // Update the UI with the downloaded bitmap on the main thread
                withContext(Dispatchers.Main) {
                    if (bitmap != null) {
                        binding.imageView.setImageBitmap(bitmap)
                    } else {
                        showToast("Failed to download image")
                    }
                }
            } catch (e: Exception) {
                // Handle exceptions and show an error message on the main thread
                withContext(Dispatchers.Main) {
                    showToast("Error: ${e.message}")
                }
            }
        }
    }

    // Function to download the image file from the provided URL and convert it to a Bitmap
    private suspend fun downloadImageFile(url: String): Bitmap? {
        try {
            val response = client.get(url) // Make an HTTP GET request to the provided URL

            // Read the response content as byte array
            // This represents the raw image data
            val bytes = response.readBytes()

            // Decode the downloaded bytes into a Bitmap
            // This decodes the array starting from the index 0 up to the byteArray size
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        } catch (e: Exception) {
            return null // Return null if there was an error downloading the image
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