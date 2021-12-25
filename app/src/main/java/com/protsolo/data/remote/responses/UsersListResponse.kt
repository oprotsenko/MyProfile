package com.protsolo.data.remote.responses

import com.protsolo.app.item.UserModel

data class UsersResponse(
    val status: String,
    val code: Int,
    val message: String?,
    val data: UsersList
)

data class UsersList(
    val users: List<UserModel>
)