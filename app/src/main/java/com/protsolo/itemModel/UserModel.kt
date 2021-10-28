package com.protsolo.itemModel

import android.os.Parcelable
import com.protsolo.utils.Constants
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    var userId: Long = 0,
    var image: String = Constants.DEFAULT_IMAGE,
    var name: String = "User Name",
    var career: String = "User career",
    var address: String = "User address"
) : Parcelable