package com.example.mycardview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mycardview.databinding.CustomCardViewBinding

// Custom Recycler View Adapter for Cards
class MyCardAdapter(
    private val itemList: List<CardItem>,
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