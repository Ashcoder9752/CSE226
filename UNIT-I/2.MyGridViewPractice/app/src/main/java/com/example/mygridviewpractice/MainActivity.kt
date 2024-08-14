package com.example.mygridviewpractice

import android.media.Image
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

        val sameImageResId = R.mipmap.ic_launcher_round

        val items = Array(30) { index ->
            GridItem(sameImageResId, "Item ${index + 1}")
        }

        val gridView = findViewById<GridView>(R.id.grid_view)
        gridView.adapter = GridAdapter(this, items)

        gridView.setOnItemClickListener { parent, view, position, id ->
            // Handle item click here
            val clickedItem = parent.getItemAtPosition(position) as GridItem
            // Do something with the clicked item
            Toast.makeText(this, "Clicked: ${clickedItem.title}", Toast.LENGTH_SHORT).show()

        }
    }
}

data class GridItem(
    val image: Int,
    val title: String
)