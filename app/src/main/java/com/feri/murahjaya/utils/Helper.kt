package com.feri.murahjaya.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import com.feri.murahjaya.R

enum class STATUS(val status: String) {
    WAITING(status = "Menunggu Pembayaran"),
    PAYED(status = "Sudah Di Bayar"),
    PROCESS(status = "Sedang Di Proses"),
    DELIVERY(status = "Pengiriman"),
    COMPLETED(status = "Selesai")
}

enum class CATEGORY(val category: String){
    BEDROOM(category = "Kamar Tidur"),
    LIVINGROOM(category = "Ruang Tengah"),
    KITCHEN(category = "Dapur")
}

enum class LIST_TYPE {
    BYCATEGORY,
    BYALL
}

enum class UPDATETYPE {
    FIRSTUSERNAME,
    USERNAME,
    PASSWORD,
    IMAGE
}

enum class ITEMTYPE {
    FAVORITE,
    GENERAL
}

enum class ADDRESSHANDLER {
    GET,
    EDIT
}

fun createToast(context: Context, message: String?) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun showLoading(activity: Activity, isShow: Boolean) {
    val progress = activity.findViewById<RelativeLayout>(R.id.progressLayout)

    if (isShow) {
        progress.visibility = View.VISIBLE
    } else {
        progress.visibility = View.GONE
    }
}