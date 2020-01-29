package com.feri.murahjaya.view.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.feri.murahjaya.R
import com.feri.murahjaya.adapter.CartAdapter
import com.feri.murahjaya.firebase.Firestore
import com.feri.murahjaya.utils.RecyclerItemTouchHelper
import com.feri.murahjaya.utils.createToast
import com.feri.murahjaya.view.CheckoutActivity
import kotlinx.android.synthetic.main.fragment_cart.*
/**
 * A simple [Fragment] subclass.
 */
class CartFragment : Fragment(), RecyclerItemTouchHelper.RecyclerItemTouchListener {

    private lateinit var adapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = Firestore(context!!).getAllCart()
        cartList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        cartList.adapter = adapter

        val itemTouchCallback = RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this)
        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(cartList)

        btCheckout.setOnClickListener {
            if (adapter.itemCount > 0) {
                val intent = Intent(context, CheckoutActivity::class.java)
                intent.putExtra("from", "carts")
                intent.putExtra("carts", adapter.getCarts())
                startActivity(intent)
            } else {
                createToast(context!!, "Keranjang Anda Kosong")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onResume() {
        super.onResume()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int) {
        if (viewHolder is CartAdapter.ViewHolder) {
            adapter.removeCartItems(viewHolder.adapterPosition)

            createToast(context!!, "Produk dihapus dari tas")
        }
    }

}
