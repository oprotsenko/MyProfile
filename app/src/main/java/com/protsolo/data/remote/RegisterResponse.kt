package com.protsolo.data.remote

import com.protsolo.app.item.UserModel

data class RegisterResponse(
    val user: UserModel,
    val accessToken: String,
    val refreshToken: String
)
