package com.protsolo.data.remote.responses

import com.protsolo.app.item.UserModel

data class AuthorizeResponse(
    val status: String,
    val code: Int,
    val message: String?,
    val data: AuthorizeResponseBody
)

data class AuthorizeResponseBody(
    val user: UserModel,
    val accessToken: String,
    val refreshToken: String
)