package com.feri.murahjaya.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.feri.murahjaya.R
import com.feri.murahjaya.firebase.Firestore
import com.feri.murahjaya.model.Address
import kotlinx.android.synthetic.main.activity_address_detail.*

class AddressDetailActivity : AppCompatActivity() {

    private lateinit var address: Address

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        address = intent.getParcelableExtra("address") as Address

        initView()

    }

    private fun initView() {
        addressName.setText(address.name)
        addressDetail.setText(address.address)
        addressKecamatan.setText(address.kecamatan)
        addressDesa.setText(address.desa)
        addressPhoneNumber.setText(address.noTelp)

        btSaveAddress.setOnClickListener {
            val update = Address(
                uid = address.uid,
                name = addressName.text.toString(),
                address = addressDetail.text.toString(),
                kecamatan = addressKecamatan.text.toString(),
                desa = addressDesa.text.toString(),
                noTelp = addressPhoneNumber.text.toString()
            )

            Firestore(this).editAddress(update)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.delete_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            val builder = AlertDialog.Builder(this)
                .setTitle("Konfirmasi")
                .setMessage("Apakah anda ingin menghapus alamat ini?")
                .setPositiveButton("Ya"){dialog, _ ->
                    Firestore(this).removeAddress(address)
                    dialog.dismiss()
                }.setNegativeButton("Tidak"){dialog, _ -> dialog.dismiss() }

            val alertDialog = builder.create()
            alertDialog.show()
        }
        return super.onOptionsItemSelected(item)
    }
}
