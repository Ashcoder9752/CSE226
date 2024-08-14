package com.example.mylistpractice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class MyAdapter(context: Context, private val items: List<ListItem>) : BaseAdapter() {

    // Use LayoutInflater.from(context) for getting the LayoutInflater instance [cleaner code]
    // private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val inflater = LayoutInflater.from(context)

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Reuse the view if possible, otherwise inflate a new view
        val view: View = convertView ?: inflater.inflate(R.layout.list_item, parent, false)

        // Get the data item for the current position
        val item = getItem(position) as ListItem

        // Find the ImageView and TextViews in the list_item layout
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val subtitleTextView: TextView = view.findViewById(R.id.subtitleTextView)

        // Set the data for the views
        imageView.setImageResource(item.iconResId)
        imageView.contentDescription = item.title
        titleTextView.text = item.title
        subtitleTextView.text = item.subtitle

        // Return the completed view to be displayed
        return view
    }
}