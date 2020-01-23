package com.feri.murahjaya.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cart(
    val product: Product,
    val qty: Int,
    val total: Int
): Parcelable