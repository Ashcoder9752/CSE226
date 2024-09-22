package com.example.mycoroutine

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.mycoroutine.databinding.ActivityFileBinding
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.request.get
import io.ktor.client.statement.readBytes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class FileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFileBinding
    private val client = HttpClient(Android) // Ktor HTTP client instance for HTTP requests

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFileBinding.inflate(layoutInflater)
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
                // Launch a coroutine to handle the file download and processing on the IO dispatcher
                downloadFile(downloadLink)
            } else {
                showToast("Please enter a valid download link")
            }
        }
    }

    // Function to download and process the PDF file
    private fun downloadFile(downloadLink: String) {
        // Launch a coroutine to handle the file download and processing on the IO dispatcher
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val file = downloadPdfFile(downloadLink) // Download the PDF file

                if (file != null) {
                    val bitmap = pdfToBitmap(file) // Convert the PDF to a bitmap
                    withContext(Dispatchers.Main) {
                        // Update the UI with the converted bitmap on the main thread
                        if (bitmap != null) {
                            binding.imageView.setImageBitmap(bitmap)
                        } else {
                            showToast("Failed to convert PDF to bitmap")
                        }
                    }
                } else {
                    // Handle the case where the file download failed
                    // As we are working on IO dispatcher, we can't update the UI from a background thread
                    // So we use the main dispatcher to show a toast message
                    withContext(Dispatchers.Main) {
                        showToast("Failed to download PDF file")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showToast("Error: ${e.message}")
                }
            }
        }
    }

    // Function to download the PDF file from the provided URL
    private suspend fun downloadPdfFile(url: String): File? {
        try {
            val response = client.get(url) // Make an HTTP GET request to the provided URL
            val content = response.readBytes() // Read the response content as bytes

            // Create a File object to save the downloaded file in the cache directory
            // The file will be named "downloaded_file.pdf"
            // It is good practice to use a unique name for each downloaded file to avoid conflicts
            // Here we have used a simple approach to generate a unique file name and store the file temporarily
            val file = File(cacheDir, "downloaded_file.pdf")
            file.writeBytes(content) // Write the downloaded content to the file
            return file
        } catch (e: Exception) {
            return null
        }
    }

    // Function to convert the downloaded PDF file to a bitmap
    private fun pdfToBitmap(file: File): Bitmap? {
        // Open the downloaded PDF file in read-only mode
        // Create a PdfRenderer instance to render the PDF file
        val fileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
        val pdfRenderer = PdfRenderer(fileDescriptor)
        val pageCount = pdfRenderer.pageCount

        // Check if there are any pages in the PDF file
        if (pageCount > 0) {
            // Open the first page of the PDF file
            val page = pdfRenderer.openPage(0)

            // Create a Bitmap object with the same dimensions as the PDF page
            // Bitmap.Config.ARGB_8888 provides 32-bit color depth with alpha channel
            val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)

            // Render the page onto the bitmap
            // The parameters are:
            // - bitmap: The bitmap to render the page into
            // - null: The rect for cropping (not used here)
            // - null: The paint object for drawing (not used here)
            // - PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY: The rendering mode for display
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            page.close()
            pdfRenderer.close()
            fileDescriptor.close()

            // Return the rendered bitmap
            return bitmap
        }

        pdfRenderer.close()
        fileDescriptor.close()
        return null // Return null if there are no pages in the PDF file
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