package com.feri.murahjaya.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feri.murahjaya.R
import com.feri.murahjaya.adapter.CheckoutAdapter
import com.feri.murahjaya.firebase.Firestore
import com.feri.murahjaya.model.*
import com.feri.murahjaya.utils.ADDRESSHANDLER
import com.feri.murahjaya.utils.STATUS
import com.feri.murahjaya.utils.createToast
import com.feri.murahjaya.utils.intToRupiah
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_checkout.*

class CheckoutActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var fromTAG: String
    private var product: Product? = null
    private var shippingCost = 100000
    private var pengiriman = "Kurir"
    private var totalBayar = 0
    private lateinit var adapter: CheckoutAdapter

    private lateinit var mUser: FirebaseUser
    private var address: Address? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Checkout"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        mUser = FirebaseAuth.getInstance().currentUser!!

        fromTAG = intent.getStringExtra("from")!!

        initViewItem()
    }

    private fun initViewItem() {

        biayaPengiriman.text = intToRupiah(shippingCost)

        adapter = CheckoutAdapter()
        manyItemList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        manyItemList.adapter = adapter

        if (fromTAG == "detail") {
            product = intent.getParcelableExtra("product") as Product
            adapter.addItem(product!!)
        } else {
            val items = intent.getParcelableArrayListExtra<Cart>("carts")!!
            adapter.addItems(items)
        }

        spinnerPengiriman.setItems("Kurir", "Ambil Sendiri")
        spinnerPengiriman.setOnItemSelectedListener { _, position, _, item ->
            pengiriman = item.toString()
            when (position) {
                0 -> {
                    shippingCost = 100000
                    paymentDetail()
                }
                1 -> {
                    shippingCost = 0
                    paymentDetail()
                }
            }
        }

        subTotal.text = intToRupiah(adapter.getTotal())
        totalPembayaran.text = intToRupiah(shippingCost + adapter.getTotal())

        btPayment.setOnClickListener(this)
        addAddress.setOnClickListener(this)
    }

    private fun paymentDetail() {
        biayaPengiriman.text = intToRupiah(shippingCost)
        subTotal.text = intToRupiah(adapter.getTotal())
        totalBayar = adapter.getTotal() + shippingCost
        totalPembayaran.text = intToRupiah(totalBayar)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btPayment -> {
                if (address != null) {
                    saveOrder()
                } else {
                    createToast(this, "Alamat tidak boleh kosong")
                }
            }
            R.id.addAddress -> {
                val intent = Intent(this, AddressActivity::class.java)
                intent.putExtra("handler", ADDRESSHANDLER.GET.ordinal)
                startActivityForResult(intent, ADDRESS_REQ)
            }
        }
    }

    private fun saveOrder() {
        val user = User(nama = mUser.displayName!!, uid = mUser.uid)
        val order = Order(
            user = user,
            address = address!!,
            pengiriman = pengiriman,
            total = adapter.getTotal() + shippingCost,
            detailOrder = adapter.getItem(),
            status = STATUS.WAITING
        )
        Firestore(this).saveOrder(order)
    }

    @Suppress("CAST_NEVER_SUCCEEDS")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == ADDRESS_REQ) {
            address = data?.getParcelableExtra("address") as Address

            addressDetail.text = address?.address
            addressKecamatanDesa.text = "${address?.kecamatan}, ${address?.desa}"
            phoneNumber.text = address?.noTelp
        }
    }

    companion object {
        const val ADDRESS_REQ = 1001
    }
}
