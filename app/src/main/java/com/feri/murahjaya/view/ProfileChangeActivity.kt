package com.feri.murahjaya.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.feri.murahjaya.R
import com.feri.murahjaya.firebase.Auth
import com.feri.murahjaya.model.AuthField
import com.feri.murahjaya.utils.NetworkState
import com.feri.murahjaya.utils.UPDATETYPE
import kotlinx.android.synthetic.main.activity_profile_change.*
import kotlinx.android.synthetic.main.activity_profile_setting.toolbar

class ProfileChangeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_change)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        val type = intent.getStringExtra("type")

        if (type == "username") {
            supportActionBar?.title = "Ganti Username"
            usernameLayout.visibility = View.VISIBLE
        } else {
            supportActionBar?.title = "Ganti Password"
            passwordLayout.visibility = View.VISIBLE
        }

        btSave.setOnClickListener {
            if (NetworkState().isNetworkConnected(this)) {
                when (type) {
                    "username" -> changeUsername()
                    "password" -> changePassword()
                }
            }
        }
    }

    private fun changePassword() {
        if (checkPasswordInput()) {
            val field = AuthField(password = newPassword.text.toString())
            Auth(this).updateType(field, UPDATETYPE.PASSWORD)
        }
    }

    private fun checkPasswordInput(): Boolean {

        if (newPassword.text.isNullOrEmpty()) {
            newPassword.error = "Tidak boleh kosong"
            newPassword.requestFocus()

            return false
        }

        if (newPassword.text.toString().length < 8) {
            newPassword.error = "Password setidaknya harus 8 karakter"
            newPassword.requestFocus()

            return false
        }

        if (confirmNewPassword.text.toString() != newPassword.text.toString()) {
            confirmNewPassword.error = "Password tidak sama"
            confirmNewPassword.requestFocus()

            return false
        }

        return true

    }

    private fun changeUsername() {
        if (checkUsername()) {
            val field = AuthField(name = userName.text.toString())
            Auth(this).updateType(field, UPDATETYPE.USERNAME)
        }
    }

    private fun checkUsername(): Boolean {

        if (userName.text.isNullOrEmpty()) {
            userName.error = "Tidak boleh kosong"
            userName.requestFocus()

            return false
        }

        return true

    }
}
