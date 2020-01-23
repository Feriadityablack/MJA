package com.feri.murahjaya.model

import android.os.Parcelable
import com.feri.murahjaya.model.Product
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailOrder(
    var product: Product,
    var total: Int = 0,
    var jumlah: Int = 0
): Parcelable