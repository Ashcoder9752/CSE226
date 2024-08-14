package com.example.mylistpractice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

// MyAdapter using ArrayAdapter

//class MyArrayAdapter(context: Context, private val items: List<ListItem>) :
//    ArrayAdapter<ListItem>(context, 0, items) {
//
//    // ArrayAdapter<ListItem>: The MyArrayAdapter class extends ArrayAdapter, a generic class that simplifies binding data to a list of views.
//    // context: Passed to the ArrayAdapter constructor, it’s used to access layout resources.
//    // 0: Resource ID for a layout file that defines the layout for each item. It’s set to 0 because we are inflating the view manually.
//
//    private val inflater = LayoutInflater.from(context)
//
//    // This method is called by the system to get the View for each item in the list.
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        // Reuse the view if possible, otherwise inflate a new view
//        val view: View = convertView ?: inflater.inflate(R.layout.list_item, parent, false)
//
//        // val item = getItem(position) as ListItem
//        // When null is encountered it tries to cast it into ListItem class.
//        // Since ListItem is a non-nullable class, it throws a ClassCastException.
//
//        // val item = getItem(position)!!
//        // When null is encountered it throws a NullPointerException. Crashing the Application.
//
//        // Get the data item for the current position
//        val item = getItem(position) ?: ListItem(
//            "Null Item",
//            "No description",
//            R.drawable.ic_launcher_foreground
//        )
//        // safe call using the Elvis operator ?:
//
//        // Find the ImageView and TextViews in the list_item layout
//        val imageView: ImageView = view.findViewById(R.id.imageView)
//        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
//        val subtitleTextView: TextView = view.findViewById(R.id.subtitleTextView)
//
//        // Set the data for the views
//        imageView.setImageResource(item.iconResId)
//        imageView.contentDescription = item.title
//        titleTextView.text = item.title
//        subtitleTextView.text = item.subtitle
//
//        // Return the completed view to be displayed
//        return view
//    }
//}

// MyAdapter using BaseAdapter

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
        // Read a key difference between ArrayAdapter and BaseAdapter bottom of the page

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

// ArrayAdapter: The getItem(position) method in ArrayAdapter can return null in certain situations.
// This is because ArrayAdapter allows you to work with nullable arrays or lists. If the underlying
// array or list contains a null value at the specified position, getItem(position) will return null.

// BaseAdapter: In contrast, the getItem(position) method in BaseAdapter is typically expected to
// return a non-null value. This is because BaseAdapter is a more general-purpose adapter, and it's
// up to the developer to ensure that the data provided to the adapter is non-nullable. Here's a
// simple analogy:

// Imagine ArrayAdapter as a container that can hold various items, including empty slots (null values).
// Think of BaseAdapter as a container that is expected to be filled with actual items, and empty slots
// are not allowed. Practical implications: