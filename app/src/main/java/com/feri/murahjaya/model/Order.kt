package com.feri.murahjaya.model

import android.os.Parcelable
import com.feri.murahjaya.utils.STATUS
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order(
    var uid: String = "",
    var user: User,
    var alamat: String = "",
    var pengiriman: String = "",
    var total: Int = 0,
    var detailOrder: List<DetailOrder>,
    var status: STATUS
): Parcelable