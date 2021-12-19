package com.protsolo.data.remote

import com.protsolo.app.item.UserModel

data class LoginResponse(
    var status: String,
    var token: String,
    var user: UserModel
)
