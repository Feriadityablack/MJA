package com.feri.murahjaya.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.feri.murahjaya.R
import com.feri.murahjaya.model.Order
import com.feri.murahjaya.utils.intToRupiah
import com.feri.murahjaya.view.OrderDetailActivity
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.order_item.view.*

class OrderAdapter(private val context: Context, options: FirestoreRecyclerOptions<Order>) :
    FirestoreRecyclerAdapter<Order, OrderAdapter.ViewHolder>(options) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(order: Order) {
            itemView.orderID.text = order.uid
            itemView.orderStatus.text = order.status?.status
            itemView.orderTotalPayment.text = intToRupiah(order.total)

            itemView.orderDate.text = "${order.createdAt?.toDate()}"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.order_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Order) {
        holder.bind(model)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, OrderDetailActivity::class.java)
            intent.putExtra("order", model)
            context.startActivity(intent)
        }
    }

}