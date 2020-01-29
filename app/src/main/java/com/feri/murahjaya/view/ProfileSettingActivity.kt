package com.feri.murahjaya.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.feri.murahjaya.R
import com.feri.murahjaya.firebase.Auth
import com.feri.murahjaya.model.AuthField
import com.feri.murahjaya.utils.UPDATETYPE
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_profile_setting.*

class ProfileSettingActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_setting)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Pengaturan Profil"
        toolbar.setNavigationOnClickListener { finish() }

        mAuth = FirebaseAuth.getInstance()

        initView()
        getUserDetail()
    }

    private fun getUserDetail() {
        val user = mAuth.currentUser
        userName.text = user?.displayName
        Glide.with(this).load(user?.photoUrl).into(userImage)
    }

    private fun initView() {
        val intent = Intent(this, ProfileChangeActivity::class.java)

        btChangeUsername.setOnClickListener {
            intent.putExtra("type", "username")
            startActivity(intent)
        }

        btChangePassword.setOnClickListener {
            intent.putExtra("type", "password")
            startActivity(intent)
        }

        userImage.setOnClickListener {
            ImagePicker.with(this)
                .start()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data

            updateImageProfile(imageUri)
        }
    }

    private fun updateImageProfile(imageUri: Uri?) {
        val field = AuthField(image = imageUri)
        Auth(this).updateType(field, UPDATETYPE.IMAGE)

        Glide.with(this).load(imageUri).into(userImage)
        onResume()
    }

    override fun onResume() {
        super.onResume()
        getUserDetail()
    }
}
