package com.feri.murahjaya.firebase

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.feri.murahjaya.utils.createToast
import com.feri.murahjaya.view.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Auth(private val context: Context){

    private val mAuth = FirebaseAuth.getInstance()
    private val activity = context as Activity

    fun signInWithEmail(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            createToast(context, "Selamat datang, $email")
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(intent)
            activity.finish()
        }.addOnFailureListener {
            createToast(context, it.localizedMessage)
        }
    }

    fun signUpWithEmail(email: String, password: String, name: String) {

    }

    fun signInWithGoogle() {

    }

}