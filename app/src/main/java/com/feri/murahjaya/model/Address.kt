package com.feri.murahjaya.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    val name: String = "",
    val address: String = "",
    val noTelp: String = ""
): Parcelable