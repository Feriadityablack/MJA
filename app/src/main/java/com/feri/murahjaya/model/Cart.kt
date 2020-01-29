package com.feri.murahjaya.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cart(
    var product: Product? = null,
    var qty: Int = 1,
    var total: Int = 0
): Parcelable