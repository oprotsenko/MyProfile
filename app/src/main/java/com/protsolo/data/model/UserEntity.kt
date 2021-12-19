package com.protsolo.data.model

data class UserEntity(
    val userId: Long,
    val image: String,
    val name: String,
    val career: String,
    val address: String,
    val phone: String,
    val birthday: String
)
