package com.feri.murahjaya.model

import android.os.Parcelable
import com.feri.murahjaya.utils.STATUS
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order(
    var uid: String? = null,
    var user: User? = null,
    var address: Address? = null,
    var pengiriman: String = "",
    var total: Int = 0,
    var detailOrder: List<Cart>? = null,
    var status: STATUS? = null,
    var buktiPembayaran: String? = null,
    @ServerTimestamp var createdAt: Timestamp? = null
) : Parcelable