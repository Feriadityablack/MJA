package com.feri.murahjaya.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var uid: String = "",
    var nama: String = ""
): Parcelable