package com.feri.murahjaya.utils

import java.text.NumberFormat
import java.util.*

fun intToRupiah(harga: Int): String {
    val locale = Locale("in", "ID")
    val numberFormat = NumberFormat.getCurrencyInstance(locale)

    return numberFormat.format(harga)
}