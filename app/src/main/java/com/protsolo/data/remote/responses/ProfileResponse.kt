package com.protsolo.data.remote.responses

import com.protsolo.app.item.UserModel

data class ProfileResponse(
    val status: String,
    val code: Int,
    val message: String?,
    val data: UserModel
)