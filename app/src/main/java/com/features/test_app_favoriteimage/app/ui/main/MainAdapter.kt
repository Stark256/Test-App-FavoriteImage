package com.features.test_app_favoriteimage.app.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.features.test_app_favoriteimage.R
import com.features.test_app_favoriteimage.db.models.DBImages
import kotlinx.android.synthetic.main.item_image.view.*

class MainAdapter(val listener: ImageClickListener) : RecyclerView.Adapter<MainAdapter.ImageViewHolder>() {

    private lateinit var context: Context
    private var dataArr = ArrayList<DBImages>()


    fun addSingleObject(imageDBModel: DBImages) {
        dataArr.add(imageDBModel)
        dataArr.sortByDescending { it.id }
        notifyDataSetChanged()
    }

    fun replaceAll(arr: ArrayList<DBImages>) {
        dataArr.clear()
        if(arr.isNotEmpty()) {
            dataArr.addAll(arr)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        this.context = parent.context
        return ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_image, parent, false))
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = dataArr[position]

        Glide.with(context)
            .load(item.image)
            .centerCrop()
            .into(holder.image)

        Glide.with(context)
            .load(
                if(item.isFavorite) ContextCompat.getDrawable(context, R.drawable.ic_favorite)
                else ContextCompat.getDrawable(context, R.drawable.ic_favorite_borde)
            ).centerCrop()
            .into(holder.favorite)

        holder.name.text = item.imageName

        holder.favorite.setOnClickListener {
            item.isFavorite = !item.isFavorite

            Glide.with(context)
                .load(
                    if(item.isFavorite) ContextCompat.getDrawable(context, R.drawable.ic_favorite)
                    else ContextCompat.getDrawable(context, R.drawable.ic_favorite_borde)
                ).centerCrop()
                .into(holder.favorite)

            notifyItemChanged(position)
            listener.onFavoritePressed(item)
        }

        holder.itemView.setOnClickListener {
            listener.onItemPressed(item)
        }

    }

    override fun getItemCount(): Int {
        return dataArr.size
    }

    class ImageViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val image: ImageView = v.iv_image
        val favorite: ImageView = v.iv_favorite
        val name: TextView = v.tv_image_name
    }

    interface ImageClickListener {
        fun onFavoritePressed(dbImages: DBImages)
        fun onItemPressed(dbImages: DBImages)
    }
}