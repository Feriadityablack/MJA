package com.feri.murahjaya.model

import android.net.Uri

data class AuthField(
    var name: String? = null,
    var password: String? = null,
    var email: String? = null,
    var image: Uri? = null
)