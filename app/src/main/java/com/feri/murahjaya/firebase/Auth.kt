package com.feri.murahjaya.firebase

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.feri.murahjaya.model.AuthField
import com.feri.murahjaya.utils.UPDATETYPE
import com.feri.murahjaya.utils.createToast
import com.feri.murahjaya.view.MainActivity
import com.feri.murahjaya.view.SignInActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

class Auth(private val context: Context){

    private val mAuth = FirebaseAuth.getInstance()
    private val activity = context as Activity

    fun signInWithEmail(authField: AuthField) {
        mAuth.signInWithEmailAndPassword(authField.email!!, authField.password!!).addOnSuccessListener {
            createToast(context, "Selamat datang, ${it.user?.email}")
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(intent)
            activity.finish()
        }.addOnFailureListener {
            createToast(context, it.localizedMessage)
        }
    }

    fun signUpWithEmail(authField: AuthField) {
        mAuth.createUserWithEmailAndPassword(authField.email!!, authField.password!!).addOnSuccessListener {
            updateType(authField, UPDATETYPE.FIRSTUSERNAME)
        }.addOnFailureListener {
            createToast(context, it.localizedMessage)
        }
    }

    fun signOut() {
        if (mAuth.currentUser != null) {
            mAuth.signOut()
            val intent = Intent(context, SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(intent)
            activity.finish()
        }
    }

    fun updateType(authField: AuthField, type: UPDATETYPE) {
        when (type) {
            UPDATETYPE.FIRSTUSERNAME -> firstCreateUpdate(authField.name!!)
            UPDATETYPE.USERNAME -> updateUsername(authField.name!!)
            UPDATETYPE.IMAGE -> updateImage(authField.image!!)
            UPDATETYPE.PASSWORD -> updatePassword(authField.password!!)
        }
    }

    private fun updatePassword(password: String) {
        mAuth.currentUser?.updatePassword(password)?.addOnSuccessListener {
            createToast(context, "Password telah diganti")
            activity.finish()
        }?.addOnFailureListener {
            createToast(context, it.localizedMessage)
        }
    }

    private fun updateImage(image: Uri) {
        val userUpdate = UserProfileChangeRequest.Builder().setPhotoUri(image).build()
        mAuth.currentUser?.updateProfile(userUpdate)?.addOnSuccessListener {

            createToast(context, "Foto telah diganti")

        }?.addOnFailureListener {
            createToast(context, it.localizedMessage)
        }
    }

    private fun updateUsername(name: String) {
        val userUpdate = UserProfileChangeRequest.Builder().setDisplayName(name).build()
        mAuth.currentUser?.updateProfile(userUpdate)?.addOnSuccessListener {

            createToast(context, "Username telah diganti")
            activity.finish()

        }?.addOnFailureListener {
            createToast(context, it.localizedMessage)
        }
    }

    private fun firstCreateUpdate(name: String) {
        val userUpdate = UserProfileChangeRequest.Builder().setDisplayName(name).build()
        mAuth.currentUser?.updateProfile(userUpdate)?.addOnSuccessListener {

            createToast(context, "Welcome to MJA, ${mAuth.currentUser?.email}")
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(intent)
            activity.finish()

        }?.addOnFailureListener {
            createToast(context, it.localizedMessage)
        }
    }

    fun isUserSignedIn() {
        if (mAuth.currentUser != null) {
            context.startActivity(Intent(context, MainActivity::class.java))
            activity.finish()
        }
    }

    fun getUserProfile(): FirebaseUser {
        return mAuth.currentUser!!
    }

    fun sendPsswordChangeRequest(authField: AuthField) {
        mAuth.sendPasswordResetEmail(authField.email!!).addOnSuccessListener {
            createToast(context, "Email telah dikirim")
        }.addOnFailureListener {
            createToast(context, it.localizedMessage)
        }
    }

    fun sendVerificationEmail() {
        mAuth.currentUser?.sendEmailVerification()?.addOnSuccessListener {
            createToast(context, "Email telah dikirim")
        }?.addOnFailureListener {
            createToast(context, it.localizedMessage)
        }
    }

    fun isUserVerified(): Boolean {
        return mAuth.currentUser?.isEmailVerified!!
    }

}