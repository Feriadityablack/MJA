package com.feri.murahjaya.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feri.murahjaya.R
import com.feri.murahjaya.adapter.ProductGridAdapter
import com.feri.murahjaya.firebase.Firestore
import com.feri.murahjaya.utils.LIST_TYPE
import kotlinx.android.synthetic.main.activity_product_list.*

class ProductListActivity : AppCompatActivity() {

    private lateinit var adapter: ProductGridAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        productList.layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)

        if (intent.getIntExtra("state", 0) == LIST_TYPE.BYCATEGORY.ordinal) {
            supportActionBar?.title = intent.getStringExtra("sortBy")
            adapter = Firestore(this).getProductByCategory(intent.getStringExtra("sortBy")!!)
            productList.adapter = adapter
        } else {
            supportActionBar?.title = "Semua Produk"
            adapter = Firestore(this).getAllProduct()
            productList.adapter = adapter
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
}
