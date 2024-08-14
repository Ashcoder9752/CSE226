package com.example.recyclerviewpractice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.RecyclerView.Adapter

class MyAdapter(
    private val androidVersions: List<AndroidVersion>,
    private val clickListener: MyItemClickListener
) : Adapter<MyViewHolder>() {
    // Creates a new ViewHolder for an item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Inflate the layout for each item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = androidVersions.size

    // Binds data to the ViewHolder at the specified position
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val androidVersion = androidVersions[position]
        // Bind the data to the ViewHolder and set up the click listener
        holder.bindData(androidVersion, clickListener)
    }
}

// ViewHolder class to hold references to the views for each item
class MyViewHolder(itemView: View) : ViewHolder(itemView) {
    private val versionName: TextView = itemView.findViewById(R.id.versionName)
    private val versionNumber: TextView = itemView.findViewById(R.id.versionNumber)

    // Binds the data from the AndroidVersion object to the TextViews
    fun bindData(androidVersion: AndroidVersion, clickListener: MyItemClickListener) {
        versionName.text = androidVersion.name
        versionNumber.text = androidVersion.version.toString()

        itemView.setOnClickListener {
            // Invoke the onItemClick method of the clickListener with the current androidVersion
            clickListener.onItemClick(androidVersion)
        }
    }
}

// Interface to handle item click events
interface MyItemClickListener {
    // Method to be implemented to define what happens when an item is clicked
    fun onItemClick(androidVersion: AndroidVersion)
}