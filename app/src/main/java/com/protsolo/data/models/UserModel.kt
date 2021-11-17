package com.protsolo.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    var userId: Long,
    var image: String,
    var name: String,
    var career: String,
    var address: String,
    var phone: String
) : Parcelable