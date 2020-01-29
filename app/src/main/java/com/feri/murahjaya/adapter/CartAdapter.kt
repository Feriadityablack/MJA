package com.feri.murahjaya.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.feri.murahjaya.R
import com.feri.murahjaya.firebase.Firestore
import com.feri.murahjaya.model.Cart
import com.feri.murahjaya.utils.intToRupiah
import com.feri.murahjaya.view.ProductDetailActivity
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.cart_item.view.*

class CartAdapter(private val context: Context, options: FirestoreRecyclerOptions<Cart>) :
    FirestoreRecyclerAdapter<Cart, CartAdapter.ViewHolder>(options){

    private val cartItem = arrayListOf<Cart>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(cart: Cart) {
            itemView.productName.text = cart.product?.nama
            itemView.productPrice.text = intToRupiah(cart.product?.harga!!)
            itemView.productQty.text = cart.qty.toString()

            Glide.with(itemView).load(cart.product?.gambar).into(itemView.productImage)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Cart) {
        cartItem.add(model)
        var qty: Int

        holder.bind(model)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("product", model.product)
            context.startActivity(intent)
        }

        holder.itemView.btAdd.setOnClickListener {
            qty = holder.itemView.productQty.text.toString().toInt()
            holder.itemView.productQty.text = "${qty + 1}"
            model.qty = qty
            Firestore(context).updateCartItem(model)
        }

        holder.itemView.btRemove.setOnClickListener {
            qty = holder.itemView.productQty.text.toString().toInt()
            if (qty >= 0) {
                holder.itemView.productQty.text = "${qty - 1}"
                model.qty = qty
                Firestore(context).updateCartItem(model)
            }
        }
    }

    fun removeCartItems(position: Int) {
        snapshots.getSnapshot(position).reference.delete()
    }

    fun getCartTotal(): Int {
        var total = 0

        for (i in cartItem.indices) {
            total += cartItem[i].qty * cartItem[i].product?.harga!!
        }

        return total

    }

    fun getCarts(): ArrayList<Cart> {
        return cartItem
    }

}