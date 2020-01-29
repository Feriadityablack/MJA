package com.feri.murahjaya.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    var uid: String? = null,
    var name: String = "",
    var address: String = "",
    var kecamatan: String? = null,
    var desa: String? = null,
    var noTelp: String = ""
): Parcelable