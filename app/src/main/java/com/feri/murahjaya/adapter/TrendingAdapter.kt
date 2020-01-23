package com.feri.murahjaya.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.feri.murahjaya.R
import kotlinx.android.synthetic.main.trending_item.view.*

class TrendingAdapter: RecyclerView.Adapter<TrendingAdapter.ViewHolder>() {

    private val images = listOf(
        R.drawable.image_1,
        R.drawable.image_2,
        R.drawable.image_3,
        R.drawable.image_4
    )

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trending_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.trendingImage.setImageResource(images[position])
        holder.itemView.trendingImage.scaleType = ImageView.ScaleType.CENTER_CROP
    }

}