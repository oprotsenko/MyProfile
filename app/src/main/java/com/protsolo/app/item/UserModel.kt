package com.protsolo.app.item

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val id: Int,
    val email: String,
    val password: String,
    val name: String?,
    val phone: String?,
    val address: String?,
    val career: String?,
    val birthday: String?,
    val image: String?
) : Parcelable

data class WrapperUserModel(
    val user: UserModel,
    var addContactView: Boolean = false,
    var isAdded: Boolean = false,
    var isSelected: Boolean = false
)