package com.feri.murahjaya.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.feri.murahjaya.R
import com.feri.murahjaya.model.Cart
import com.feri.murahjaya.model.Product
import com.feri.murahjaya.utils.intToRupiah
import kotlinx.android.synthetic.main.checkout_item.view.*

class CheckoutAdapter : RecyclerView.Adapter<CheckoutAdapter.ViewHolder>() {

    private val carts = mutableListOf<Cart>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(cart: Cart) {
            itemView.productName.text = cart.product?.nama
            itemView.productPrice.text = intToRupiah(cart.product!!.harga)
            itemView.productQty.text = cart.qty.toString()

            itemView.btAdd.visibility = View.GONE
            itemView.btRemove.visibility = View.GONE

            Glide.with(itemView).load(cart.product?.gambar).into(itemView.productImage)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.checkout_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = carts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(carts[position])
    }

    fun addItems(carts: List<Cart>) {
        this.carts.removeAll(this.carts)
        this.carts.addAll(carts)
        notifyDataSetChanged()
    }

    fun addItem(product: Product) {
        this.carts.clear()
        val cart = Cart(product)
        carts.add(cart)
        notifyDataSetChanged()
    }

    fun getItem(): List<Cart> {
        return carts
    }

    fun getTotal(): Int {
        var total = 0
        for (i in carts.indices) {
            total += (carts[i].qty * carts[i].product!!.harga)
        }

        return total
    }

}