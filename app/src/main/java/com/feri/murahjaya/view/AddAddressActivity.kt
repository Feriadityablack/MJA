package com.feri.murahjaya.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.feri.murahjaya.R
import com.feri.murahjaya.firebase.Firestore
import com.feri.murahjaya.model.Address
import com.feri.murahjaya.utils.NetworkState
import com.feri.murahjaya.utils.createToast
import kotlinx.android.synthetic.main.activity_add_address.*

class AddAddressActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Tambah Alamat"
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        btSaveAddress.setOnClickListener {
            if (NetworkState().isNetworkConnected(this)) {
                if (checkInput()) {
                    val address = Address(
                        name = addressName.text.toString(),
                        address = addressDetail.text.toString(),
                        kecamatan = addressKecamatan.text.toString(),
                        desa = addressDesa.text.toString(),
                        noTelp = addressPhoneNumber.text.toString()
                    )
                    Firestore(this).addAddress(address)
                }
            } else {
                createToast(this, "Mohon cek koneksi internet anda.")
            }
        }
    }

    private fun checkInput(): Boolean {
        if (addressName.text.isNullOrEmpty()) {
            addressName.error = "Tidak boleh kosong"
            addressName.requestFocus()

            return false
        }

        if (addressDetail.text.isNullOrEmpty()) {
            addressDetail.error = "Tidak boleh kosong"
            addressDetail.requestFocus()

            return false
        }

        if (addressPhoneNumber.text.isNullOrEmpty()) {
            addressPhoneNumber.error = "Tidak boleh kosong"
            addressPhoneNumber.requestFocus()

            return false
        }

        return true
    }
}
