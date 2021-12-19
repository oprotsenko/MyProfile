package com.protsolo.data.remote

data class RegisterUserRequest(
    val email: String,
    val password: String,
    val name: String? = null,
    val phone: String? = null,
    val address: String? = null,
    val career: String? = null,
    val image: String? = null,
    val birthday: String? = null
)
