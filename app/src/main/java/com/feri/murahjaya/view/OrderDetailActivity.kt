package com.feri.murahjaya.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.feri.murahjaya.R
import com.feri.murahjaya.adapter.CheckoutAdapter
import com.feri.murahjaya.firebase.Firestore
import com.feri.murahjaya.model.Order
import com.feri.murahjaya.utils.createToast
import com.feri.murahjaya.utils.intToRupiah
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.activity_order_detail.*

class OrderDetailActivity : AppCompatActivity() {

    private lateinit var order: Order
    private lateinit var adapter: CheckoutAdapter
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Order Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        order = intent.getParcelableExtra("order") as Order
        adapter = CheckoutAdapter()

        initView()
    }

    private fun initView() {
        status.text = order.status?.status
        addressDetail.text = order.address?.address
        addressKecamatanDesa.text = "${order.address?.kecamatan}, ${order.address?.desa}"
        addressPhoneNumber.text = order.address?.noTelp
        shippingType.text = order.pengiriman

        val pengiriman: Int

        if (order.pengiriman == "Kurir") {
            pengiriman = 100000
        } else {
            pengiriman = 0
        }

        productList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        productList.adapter = adapter

        adapter.addItems(order.detailOrder!!)

        subTotal.text = intToRupiah(adapter.getTotal())
        biayaPengiriman.text = intToRupiah(pengiriman)
        totalPembayaran.text = intToRupiah(order.total)

        if (!order.buktiPembayaran.isNullOrEmpty()) {
            btUpload.isEnabled = false
            buktiPembayaran.isEnabled = false

            Glide.with(this).load(order.buktiPembayaran).into(buktiPembayaran)
            btPayment.visibility = View.GONE
            btCancelOrder.visibility = View.GONE
        }

        buktiPembayaran.setOnClickListener {
            ImagePicker.with(this).compress(1024).start()
        }

        btUpload.setOnClickListener {
            if (imageUri != null) {
                Firestore(this).uploadBuktiPembayaran(imageUri!!, order)
            } else {
                createToast(this, "Gambar tidak boleh kosong")
            }
        }

        btPayment.setOnClickListener {
            val intent = Intent(this, PaymentActivity::class.java)
            intent.putExtra("TAG", "detail")
            startActivity(intent)
        }

        btCancelOrder.setOnClickListener {
            Firestore(this).deleteOrder(order)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            imageUri = data?.data

            buktiPembayaran.setImageURI(imageUri)
            buktiPembayaran.scaleType = ImageView.ScaleType.CENTER_CROP
        }
    }
}
