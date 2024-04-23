package com.bangkit.nyancat.ui.main

import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bangkit.nyancat.R
import com.bangkit.nyancat.data.response.SearchCatResponse
import com.bumptech.glide.Glide

class CatListAdapter(private val context: Context, private val listCat: List<SearchCatResponse>) : RecyclerView.Adapter<CatListAdapter.ViewHolder>() {
    
    private lateinit var onItemClickCallback: OnItemClickCallback
    
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_cat, viewGroup, false))
    
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currentData = listCat[position]
        val imageUrl = "https://cdn2.thecatapi.com/images/" + currentData.referenceImageId + ".jpg"
        Glide.with(context)
            .load(imageUrl)
            .placeholder(R.drawable.cat_silhoutte)
            .into(viewHolder.image)
        viewHolder.tvCatName.text = listCat[position].name
        viewHolder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listCat[position])}
    }
    
    override fun getItemCount() = listCat.size
    
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.imageViewCatCard)
        val tvCatName: TextView = view.findViewById(R.id.tvCatCard)
    }
    
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    
    interface OnItemClickCallback {
        fun onItemClicked(catDetail: SearchCatResponse)
    }
    
}
