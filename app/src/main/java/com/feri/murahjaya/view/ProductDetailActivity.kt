package com.feri.murahjaya.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.feri.murahjaya.R
import com.feri.murahjaya.model.Product
import com.feri.murahjaya.utils.intToRupiah
import kotlinx.android.synthetic.main.activity_product_detail.*

class ProductDetailActivity : AppCompatActivity() {

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

    }

    private fun initDetailProduct() {
        val product = intent.getParcelableExtra("product") as Product
        productName.text = product.nama
        productPrice.text = intToRupiah(product.harga)
        productStok.text = product.stok.toString()
        productDescription.text = product.detail

        if (!product.gambar.isNullOrEmpty()) {
            Glide.with(this).load(product.gambar).centerCrop().into(productImage)
        }
    }
}
