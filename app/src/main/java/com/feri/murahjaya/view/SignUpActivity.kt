package com.feri.murahjaya.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.feri.murahjaya.R
import com.feri.murahjaya.firebase.Auth
import com.feri.murahjaya.model.AuthField
import com.feri.murahjaya.utils.NetworkState
import com.feri.murahjaya.utils.createToast
import kotlinx.android.synthetic.main.activity_product_list.*
import kotlinx.android.synthetic.main.activity_product_list.toolbar
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        bt_sign_up.setOnClickListener {
            if (NetworkState().isNetworkConnected(this)) {
                if (checkInput()) {
                    val auth = AuthField(
                        name = user_name.text.toString(),
                        email = user_email.text.toString(),
                        password = user_password.text.toString()
                    )

                    Auth(this).signUpWithEmail(auth)
                }
            } else {
                createToast(this, "Mohon periksa koneksi internet anda")
            }
        }
    }

    private fun checkInput(): Boolean {

        if (user_name.text.isNullOrEmpty()) {
            user_name.error = "Nama tidak boleh kosong"
            user_name.requestFocus()

            return false
        }

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

        if (user_password.text.toString().length < 8) {
            user_password.error = "Password harus lebih dari 8 karakter"
            user_password.requestFocus()

            return false
        }

        if (confirm_password.text.toString() != user_password.text.toString()) {
            confirm_password.error = "Password tidak sama"
            confirm_password.requestFocus()

            return false
        }

        return true

    }
}
