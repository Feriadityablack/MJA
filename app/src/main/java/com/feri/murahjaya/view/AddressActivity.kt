package com.feri.murahjaya.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feri.murahjaya.R
import com.feri.murahjaya.adapter.AddressAdapter
import com.feri.murahjaya.firebase.Firestore
import com.feri.murahjaya.utils.ADDRESSHANDLER
import kotlinx.android.synthetic.main.activity_address.*

class AddressActivity : AppCompatActivity() {

    private lateinit var adapter: AddressAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = "Alamat Pengiriman"
        toolbar.setNavigationOnClickListener { finish() }

        val handler = intent.getIntExtra("handler", 0)

        adapter = if (handler == 0) Firestore(this).getUserAddress(ADDRESSHANDLER.GET)
        else Firestore(this).getUserAddress(ADDRESSHANDLER.EDIT)

        addressList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        addressList.adapter = adapter

        fabAdd.setOnClickListener {
            startActivity(Intent(this, AddAddressActivity::class.java))
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
