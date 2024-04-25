package com.bangkit.nyancat.ui.favorite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.nyancat.R
import com.bangkit.nyancat.database.Cat
import com.bumptech.glide.Glide

class FavoriteListAdapter(private val context: Context, private var listCat: List<Cat>) :
RecyclerView.Adapter<FavoriteListAdapter.ViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallback
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val imageView: ImageView = itemView.findViewById(R.id.imageViewCatCard)
        private val textView: TextView = itemView.findViewById(R.id.tvCatCard)

        fun bind(cat: Cat){
            Glide.with(context)
                .load(cat.avatar_url)
                .placeholder(R.drawable.cat_silhoutte)
                .into(imageView)
            textView.text = cat.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cat, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCat = listCat[position]
        holder.bind(currentCat)
        holder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClicked(currentCat)
        }

    }
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(cat: Cat)
    }

    override fun getItemCount(): Int {
        return listCat.size
    }
}