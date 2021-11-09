package com.protsolo.itemModel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val userId: Long,
    var image: String,
    var name: String,
    var career: String,
    var address: String,
    var phone: String,
    var selected: Boolean = false
) : Parcelable {

    fun setUserSelected(selected: Boolean) {
        this.selected = selected
    }

    fun isSelected() = selected
}