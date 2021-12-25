package com.protsolo.data.remote.requests

data class EditProfileRequest(
    val name: String? = null,
    val phone: String? = null,
    val address: String? = null,
    val career: String? = null,
    val image: String? = null,
    val birthday: String? = null
)
