package com.protsolo.app.item

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val userId: Long,
    val image: String,
    val name: String,
    val career: String,
    val address: String,
    val phone: String,
    val birthday: String
) : Parcelable

data class WrapperUserModel(
    val user: UserModel,
    var isSelected: Boolean = false
)