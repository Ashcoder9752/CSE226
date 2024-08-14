package com.example.mylistpractice

import android.os.Bundle
import android.widget.ListView
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

        // Sample data for the list
        val items = listOf(
            ListItem("Title 1", "Subtitle 1", R.drawable.ic_launcher_background),
            ListItem("Title 2", "Subtitle 2", R.drawable.ic_launcher_background)
        )

        // Get the ListView from the layout
        val listView: ListView = findViewById(R.id.listView)

        // Create an adapter and set it to the ListView
        val adapter = MyAdapter(this, items)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            // Handle the click event
            val item = parent.getItemAtPosition(position) as ListItem
            Toast.makeText(this, "Clicked: ${item.title}", Toast.LENGTH_SHORT).show()
        }
    }
}

// Used data class because it gives equals(), hashCode(), toString(), and copy() method automatically.
// Benefit is that it operates these methods on data rather than on object.
data class ListItem(
    val title: String,
    val subtitle: String,
    val iconResId: Int
)