package com.feri.murahjaya.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.feri.murahjaya.R
import com.feri.murahjaya.model.Product
import com.feri.murahjaya.utils.intToRupiah
import kotlinx.android.synthetic.main.product_grid_item.view.*

class SearchAdapter(private val context: Context) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>(), Filterable {

    private var products = listOf<Product>()
    private var filteredProducts = products
    private var customFilter: CustomFilter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.product_grid_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(product: Product) {
            itemView.productName.text = product.nama
            itemView.productPrice.text = intToRupiah(product.harga)

            Glide.with(itemView).load(product.gambar).into(itemView.productImage)
        }

    }

    fun addItems(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        if (customFilter == null) {
            customFilter = CustomFilter(this, filteredProducts)
        }

        return customFilter!!
    }

    inner class CustomFilter(val adapter: SearchAdapter, val filterList: List<Product>) : Filter() {

        @SuppressLint("DefaultLocale")
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val results = FilterResults()
            var cons = constraint

            if (cons != null && cons.isNotEmpty()) {
                cons = cons.toString().toLowerCase()
                val filterProduct = mutableListOf<Product>()

                for (i in filterList.indices) {
                    if (filterList[i].nama.contains(cons)) {
                        filterProduct.add(filterList[i])
                    }
                }

                results.count = filterProduct.size
                results.values = filterProduct
            } else {
                results.count = filterList.size
                results.values = filterList
            }

            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            adapter.filteredProducts = results?.values as List<Product>

            adapter.notifyDataSetChanged()
        }

    }

}