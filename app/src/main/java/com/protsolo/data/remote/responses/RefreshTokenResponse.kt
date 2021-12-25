package com.protsolo.data.remote.responses

data class RefreshTokenResponse(
    val status: String,
    val code: Int,
    val message: String?,
    val data: RefreshTokenResponseBody
)

data class RefreshTokenResponseBody(
    val accessToken: String,
    val refreshToken: String
)