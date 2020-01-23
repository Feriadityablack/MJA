package com.feri.murahjaya.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.feri.murahjaya.R
import com.feri.murahjaya.utils.CATEGORY
import com.feri.murahjaya.utils.LIST_TYPE
import com.feri.murahjaya.view.ProductListActivity
import kotlinx.android.synthetic.main.category_item.view.*

class CategoryAdapter(private val context: Context) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private val icons = listOf(
        R.drawable.ic_sofa,
        R.drawable.ic_mattress,
        R.drawable.ic_kitchen
    )
    private val names = listOf(
        CATEGORY.LIVINGROOM.category,
        CATEGORY.BEDROOM.category,
        CATEGORY.KITCHEN.category
    )

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = icons.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.categoryImage.setImageResource(icons[position])
        holder.itemView.categoryName.text = names[position]

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductListActivity::class.java)
            intent.putExtra("state", LIST_TYPE.BYCATEGORY.ordinal)
            intent.putExtra("sortBy", names[position])
            context.startActivity(intent)
        }
    }

}