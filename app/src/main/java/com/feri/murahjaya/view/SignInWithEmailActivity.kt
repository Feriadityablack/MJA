package com.feri.murahjaya.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.feri.murahjaya.R
import com.feri.murahjaya.firebase.Auth
import com.feri.murahjaya.model.AuthField
import com.feri.murahjaya.utils.NetworkState
import com.feri.murahjaya.utils.createToast
import kotlinx.android.synthetic.main.activity_product_list.toolbar
import kotlinx.android.synthetic.main.activity_sign_in_with_email.*

class SignInWithEmailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_with_email)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener { finish() }

        bt_sign_in.setOnClickListener {
            if (NetworkState().isNetworkConnected(this)) {
                if (checkInput()) {
                    val auth = AuthField(
                        email = user_email.text.toString(),
                        password = user_password.text.toString()
                    )

                    Auth(this).signInWithEmail(auth)
                }
            } else {
                createToast(this, "Mohon periksa koneksi anda")
            }
        }
    }

    private fun checkInput(): Boolean {

        if (user_email.text.isNullOrEmpty()) {
            user_email.error = "Email tidak boleh kosong"
            user_email.requestFocus()

            return false
        }

        if (user_password.text.isNullOrEmpty()) {
            user_password.error = "Password tidak boleh kosong"
            user_password.requestFocus()

            return false
        }

        return true

    }
}
