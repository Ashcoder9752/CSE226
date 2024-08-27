package com.example.mycardview

import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

// MyItemTouchHelperCallback class handles swipe and drag-and-drop actions for the RecyclerView
class MyItemTouchHelperCallback(
    private val adapter: MyCardAdapter,             // Adapter managing the items in the RecyclerView
    private val snackbarHandler: SnackbarHandler    // Interface to handle Snackbar operations, passed from Activity
) : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,     // Drag Directions
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT  // Swipe Directions
    ) {
    // Called when an item is dragged and dropped to a different position
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        // Get positions of the dragged item and the target position
        val fromPosition = viewHolder.adapterPosition
        val toPosition = target.adapterPosition

        // Move item within the adapter and notify RecyclerView of the change
        adapter.onItemMove(fromPosition, toPosition)
        return true
    }

    // Called when an item is swiped (left or right)
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        // Get the position of the swiped item
        val position = viewHolder.adapterPosition
        // Retrieve the item that was swiped
        val removedItem = adapter.getItem(position)

        // Remove the item from the adapter and notify RecyclerView
        adapter.onItemDismiss(position)

        // Show an undo Snackbar, allowing the user to undo the deletion
        snackbarHandler.showUndoSnackbar(adapter, removedItem, position)
    }
}

// SnackbarHandler interface defines a method for showing an undo Snackbar
interface SnackbarHandler {
    // SnackbarHandler interface defines a method for showing an undo Snackbar
    fun showUndoSnackbar(adapter: MyCardAdapter, removedCard: CardItem, position: Int)
}