package com.feri.murahjaya.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.feri.murahjaya.R
import com.feri.murahjaya.firebase.Firestore
import com.feri.murahjaya.model.Cart
import com.feri.murahjaya.model.Product
import com.feri.murahjaya.utils.intToRupiah
import kotlinx.android.synthetic.main.activity_product_detail.*

class ProductDetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationIcon(R.drawable.back_with_circle_bg)
        toolbar.setNavigationOnClickListener { finish() }

        initDetailProduct()

        btAddToCart.setOnClickListener(this)
        btBuy.setOnClickListener(this)

    }

    private fun initDetailProduct() {
        product = intent.getParcelableExtra("product") as Product
        productName.text = product.nama
        productPrice.text = intToRupiah(product.harga)
        productStok.text = "Stok : ${product.stok}"
        productDescription.text = product.detail

        if (!product.gambar.isNullOrEmpty()) {
            Glide.with(this).load(product.gambar).centerCrop().into(productImage)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btAddToCart -> {
                val cart = Cart(
                    product = product,
                    qty = 1,
                    total = product.harga
                )
                Firestore(this).addProductToCart(cart)
            }
            R.id.btBuy -> {
                val intent = Intent(this, CheckoutActivity::class.java)
                intent.putExtra("product", product)
                intent.putExtra("from", "detail")
                startActivity(intent)
            }
        }
    }
}
