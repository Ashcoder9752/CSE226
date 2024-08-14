package com.example.recyclerviewpractice

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// MainActivity class extending AppCompatActivity and implementing MyItemClickListener
class MainActivity : AppCompatActivity(), MyItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // List of Android versions with their names and version numbers
        val androidVersions = listOf(
            AndroidVersion("No official name", 1.0),
            AndroidVersion("No official name", 1.1),
            AndroidVersion("Cupcake", 1.5),
            AndroidVersion("Donut", 1.6),
            AndroidVersion("Eclair", 2.1),
            AndroidVersion("Froyo", 2.2),
            AndroidVersion("Gingerbread", 2.3),
            AndroidVersion("Honeycomb", 3.2),
            AndroidVersion("Ice Cream Sandwich", 4.0),
            AndroidVersion("Jelly Bean", 4.3),
            AndroidVersion("KitKat", 4.4),
            AndroidVersion("Lollipop", 5.1),
            AndroidVersion("Marshmallow", 6.0),
            AndroidVersion("Nougat", 7.1),
            AndroidVersion("Oreo", 8.1),
            AndroidVersion("Pie", 9.0),
            AndroidVersion("Quince Tart", 10.0),
            AndroidVersion("Red Velvet Cake", 11.0),
            AndroidVersion("Snow Cone", 12.1),
            AndroidVersion("Tiramisu", 13.0),
            AndroidVersion("Upside Down Cake", 14.0),
            AndroidVersion("Vanilla Ice Cream", 15.0),
        )

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        // Set the layout manager for the RecyclerView to LinearLayoutManager
        // This determines how the items are laid out (vertically in this case)
        // false represent whether the items should be displayed in reverse order
        recyclerView.layoutManager =
            LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        // Set the adapter for the RecyclerView, providing the data list and the click listener
        recyclerView.adapter = MyAdapter(androidVersions, this)
    }

    // Override the onItemClick method from the MyItemClickListener interface
    override fun onItemClick(androidVersion: AndroidVersion) {
        Toast.makeText(this, "Clicked on ${androidVersion.name}", Toast.LENGTH_SHORT).show()
    }
}

// Data class holding the name and version of an Android version
data class AndroidVersion(
    val name: String,
    val version: Double
)