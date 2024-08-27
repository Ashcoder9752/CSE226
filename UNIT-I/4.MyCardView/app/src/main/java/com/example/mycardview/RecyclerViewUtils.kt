package com.example.mycardview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mycardview.databinding.CustomCardViewBinding
import java.util.Collections

// Custom Recycler View Adapter for Cards
class MyCardAdapter(
    private val itemList: MutableList<CardItem>,
    private val listener: MyItemClickListener
) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // This line of code efficiently creates a new binding instance for the custom card view layout,
        // allowing you to interact with the views in a type-safe manner and without directly using findViewById().

        // This approach simplifies view management and improves code readability.
        // CustomCardViewBinding - is the name of the layout file it is referring to.
        val binding =
            CustomCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val cardItem = itemList[position]
        holder.bindData(cardItem, listener)
    }

    fun getItem(position: Int): CardItem = itemList[position]

    // Handle item movement within the RecyclerView
    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        // Swap the items in the list at the specified positions
        Collections.swap(itemList, fromPosition, toPosition)
        // Notify the adapter that the item has moved
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    // Handle item dismissal (e.g., swipe to delete)
    fun onItemDismiss(position: Int) {
        // Remove the item from the list at the specified position
        itemList.removeAt(position)
        // Notify the adapter that the item has been removed
        notifyItemRemoved(position)
    }

    // Undo the deletion of an item
    fun undoDelete(removedItem: CardItem, position: Int) {
        // Add the removed item back to the list at the specified position
        itemList.add(position, removedItem)
        // Notify the adapter that the item has been inserted
        notifyItemInserted(position)
    }
}

// binding.root - refers to the custom layout which is to be inflated in the parent
class MyViewHolder(private val binding: CustomCardViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindData(cardItem: CardItem, listener: MyItemClickListener) {
        // set data by directly accessing the the views through view binding
        binding.cardImage.setImageResource(cardItem.image)
        binding.cardImage.contentDescription = cardItem.title
        binding.cardTitle.text = cardItem.title
        binding.cardDescription.text = cardItem.description

        itemView.setOnClickListener {
            listener.onItemClicked(cardItem)
        }
    }
}

interface MyItemClickListener {
    fun onItemClicked(cardItem: CardItem)
}