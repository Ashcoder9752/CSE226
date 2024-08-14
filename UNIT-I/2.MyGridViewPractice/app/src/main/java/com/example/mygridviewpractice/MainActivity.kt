package com.example.mygridviewpractice

import android.os.Bundle
import android.widget.GridView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Sample resource ID for the image to be used in each grid item
        val sameImageResId = R.mipmap.ic_launcher_round

        // Create an array of GridItem objects
        val items = Array(30) { index ->
            GridItem(sameImageResId, "Item ${index + 1}")
        }

        val gridView: GridView = findViewById(R.id.grid_view)
        gridView.adapter = GridAdapter(this, items)

        // Set an item click listener to handle clicks on grid items
        gridView.setOnItemClickListener { parent, view, position, id ->
            val clickedItem = parent.getItemAtPosition(position) as GridItem
            Toast.makeText(this, "Clicked: ${clickedItem.title}", Toast.LENGTH_SHORT).show()
        }
    }
}

// Data class representing a grid item with an image and title
data class GridItem(
    val image: Int,
    val title: String
)