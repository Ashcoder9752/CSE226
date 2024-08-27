package com.example.mycardview

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), MyItemClickListener, SnackbarHandler {
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
        val cardList = mutableListOf(
            CardItem(R.drawable.sample_image, "Card 1", "This is card 1"),
            CardItem(R.drawable.sample_image, "Card 2", "This is card 2"),
            CardItem(R.drawable.sample_image, "Card 3", "This is card 3"),
            CardItem(R.drawable.sample_image, "Card 4", "This is card 4"),
            CardItem(R.drawable.sample_image, "Card 5", "This is card 5"),
        )

        // set recycler view
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        val adapter = MyCardAdapter(cardList, this)
        recyclerView.adapter = adapter

        // set ItemTouchHelper - swipe and drag and functionality
        val itemTouchHelperCallback = MyItemTouchHelperCallback(adapter, this)
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    // set click listener
    override fun onItemClicked(cardItem: CardItem) {
        Toast.makeText(this, "Clicked: ${cardItem.title}", Toast.LENGTH_SHORT).show()
    }

    // Show an undo Snackbar when an item is deleted
    override fun showUndoSnackbar(adapter: MyCardAdapter, removedCard: CardItem, position: Int) {
        // Create a Snackbar to notify the user that an item has been deleted with an option to undo the deletion
        Snackbar.make(findViewById(R.id.main), "Undo", Snackbar.LENGTH_LONG)
            .setAction("Undo") {
                // If the user clicks "Undo," restore the removed item in its original position
                adapter.undoDelete(removedCard, position)
            }.show()
    }
}

data class CardItem(
    val image: Int,
    val title: String,
    val description: String
)