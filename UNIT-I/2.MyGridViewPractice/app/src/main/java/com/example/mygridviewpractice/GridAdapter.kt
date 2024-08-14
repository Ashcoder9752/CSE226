package com.example.mygridviewpractice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class GridAdapter(context: Context, private val items: Array<GridItem>) : BaseAdapter() {

    private val inflater = LayoutInflater.from(context)

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: inflater.inflate(R.layout.grid_item, parent, false)

        val item = items[position]

        view.findViewById<ImageView>(R.id.grid_item_image).setImageResource(item.image)
        view.findViewById<ImageView>(R.id.grid_item_image).contentDescription = item.title
        view.findViewById<TextView>(R.id.grid_item_text).text = item.title

        return view
    }
}