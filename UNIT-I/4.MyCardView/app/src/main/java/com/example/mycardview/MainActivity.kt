package com.example.mycardview

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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

        // Sample data
        val cardList = listOf(
            CardItem(R.drawable.sample_image, "Card 1", "This is card 1"),
            CardItem(R.drawable.sample_image, "Card 2", "This is card 2"),
            CardItem(R.drawable.sample_image, "Card 3", "This is card 3"),
            CardItem(R.drawable.sample_image, "Card 4", "This is card 4"),
            CardItem(R.drawable.sample_image, "Card 5", "This is card 5"),
        )

        // set recycler view
        val cardRecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        cardRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        cardRecyclerView.adapter = MyCardAdapter(cardList, this)
    }

    // set click listener
    override fun onItemClicked(cardItem: CardItem) {
        Toast.makeText(this, "Clicked: ${cardItem.title}", Toast.LENGTH_SHORT).show()
    }
}

data class CardItem(
    val image: Int,
    val title: String,
    val description: String
)