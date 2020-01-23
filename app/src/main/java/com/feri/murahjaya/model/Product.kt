package com.feri.murahjaya.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    var uid: String? = null,
    var nama: String = "",
    var gambar: String? = null,
    var harga: Int = 0,
    var kategori: String = "",
    var stok: Int = 0,
    var detail: String = ""
): Parcelable