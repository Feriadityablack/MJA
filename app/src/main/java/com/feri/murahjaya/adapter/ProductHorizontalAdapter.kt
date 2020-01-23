package com.feri.murahjaya.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.feri.murahjaya.R
import com.feri.murahjaya.model.Product
import com.feri.murahjaya.utils.intToRupiah
import com.feri.murahjaya.view.ProductDetailActivity
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.product_horizontal_item.view.*

class ProductHorizontalAdapter(private val context: Context, options: FirestoreRecyclerOptions<Product>) :
    FirestoreRecyclerAdapter<Product, ProductHorizontalAdapter.ViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.product_horizontal_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Product) {
        holder.bind(model)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("product", model)
            context.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(product: Product) {
            itemView.productName.text = product.nama
            itemView.productPrice.text = intToRupiah(product.harga)

            if (product.gambar != null) {
                Glide.with(itemView).load(product.gambar).centerCrop().into(itemView.productImage)
            }

        }
    }

}