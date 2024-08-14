package com.example.mygridviewpractice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class GridAdapter(context: Context, private val items: Array<GridItem>) : BaseAdapter() {

    // LayoutInflater instance for inflating views
    private val inflater = LayoutInflater.from(context)

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // Inflate a new view if convertView is null; otherwise, reuse the existing view
        val view = convertView ?: inflater.inflate(R.layout.grid_item, parent, false)

        // Retrieve the data item for the current position
        val item = items[position]

        // Find the ImageView and TextView in the grid_item layout
        val imageView: ImageView = view.findViewById(R.id.grid_item_image)
        val textView: TextView = view.findViewById(R.id.grid_item_text)

        // Set the data for the views
        imageView.setImageResource(item.image)
        imageView.contentDescription = item.title // For accessibility
        textView.text = item.title

        // Return the view for display
        return view
    }
}